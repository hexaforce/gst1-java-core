/*
 * This file is part of gstreamer-java.
 * 
 * This code is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License version 3 only, as published by the Free Software Foundation.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License version 3 for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3 along with this work.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.glib.Natives.registration;
import static org.freedesktop.gstreamer.lowlevel.GlibAPI.GLIB_API;
import static org.freedesktop.gstreamer.lowlevel.GstAPI.GST_API;
import static org.freedesktop.gstreamer.lowlevel.GstParseAPI.GSTPARSE_API;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.freedesktop.gstreamer.controller.Controllers;
import org.freedesktop.gstreamer.elements.Elements;
import org.freedesktop.gstreamer.event.Event;
import org.freedesktop.gstreamer.glib.GError;
import org.freedesktop.gstreamer.glib.GLib;
import org.freedesktop.gstreamer.glib.GMainContext;
import org.freedesktop.gstreamer.glib.MainContextExecutorService;
import org.freedesktop.gstreamer.glib.NativeObject;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GErrorStruct;
import org.freedesktop.gstreamer.lowlevel.GstTypes;
import org.freedesktop.gstreamer.message.Message;
import org.freedesktop.gstreamer.query.Query;
import org.freedesktop.gstreamer.video.Video;
import org.freedesktop.gstreamer.webrtc.WebRTC;

public final class Gst {
  private static final Logger LOG = Logger.getLogger(Gst.class.getName());
  private static final AtomicInteger INIT_COUNT = new AtomicInteger(0);
  private static final boolean CHECK_VERSIONS = !Boolean.getBoolean("gstreamer.suppressVersionChecks");
  private static final boolean DISABLE_EXTERNAL = Boolean.getBoolean("gstreamer.disableExternalTypes");
  private static ScheduledExecutorService executorService;
  private static volatile CountDownLatch quit = new CountDownLatch(1);
  private static GMainContext mainContext;
  private static boolean useDefaultContext = false;
  private static List<Runnable> shutdownTasks = Collections.synchronizedList(new ArrayList<Runnable>());
  private static int minorVersion = Integer.MAX_VALUE;

  private static class NativeArgs {
    public IntByReference argcRef;
    public PointerByReference argvRef;
    Memory[] argsCopy;
    Memory argvMemory;

    public NativeArgs(String progname, String[] args) {
      argsCopy = new Memory[args.length + 2];
      argvMemory = new Memory(argsCopy.length * Native.POINTER_SIZE);
      Memory arg = new Memory(progname.getBytes().length + 4);
      arg.setString(0, progname);
      argsCopy[0] = arg;
      for (int i = 0; i < args.length; i++) {
        arg = new Memory(args[i].getBytes().length + 1);
        arg.setString(0, args[i]);
        argsCopy[i + 1] = arg;
      }
      argvMemory.write(0, argsCopy, 0, argsCopy.length);
      argvRef = new PointerByReference(argvMemory);
      argcRef = new IntByReference(args.length + 1);
    }

    String[] toStringArray() {
      List<String> args = new ArrayList<String>();
      Pointer argv = argvRef.getValue();
      for (int i = 1; i < argcRef.getValue(); i++) {
        Pointer arg = argv.getPointer(i * Native.POINTER_SIZE);
        if (arg != null) {
          args.add(arg.getString(0));
        }
      }
      return args.toArray(new String[args.size()]);
    }
  }

  private Gst() {}

  public static Version getVersion() {
    long[] major = {0}, minor = {0}, micro = {0}, nano = {0};
    GST_API.gst_version(major, minor, micro, nano);
    return new Version((int) major[0], (int) minor[0], (int) micro[0], (int) nano[0]);
  }

  public static String getVersionString() {
    return GST_API.gst_version_string();
  }

  public static boolean isSegTrapEnabled() {
    return GST_API.gst_segtrap_is_enabled();
  }

  public static void setSegTrap(boolean enabled) {
    GST_API.gst_segtrap_set_enabled(enabled);
  }

  public static final synchronized boolean isInitialized() {
    return INIT_COUNT.get() > 0;
  }

  public static ScheduledExecutorService getExecutor() {
    return executorService;
  }

  public static void quit() {
    quit.countDown();
  }

  public static Element parseLaunch(String pipelineDescription, List<GError> errors) {
    Pointer[] err = {null};
    Element pipeline = GSTPARSE_API.gst_parse_launch(pipelineDescription, err);
    if (pipeline == null) {
      throw new GstException(extractError(err[0]));
    }
    if (err[0] != null) {
      if (errors != null) {
        errors.add(extractError(err[0]));
      } else {
        LOG.log(Level.WARNING, extractError(err[0]).getMessage());
      }
    }
    return pipeline;
  }

  public static Element parseLaunch(String pipelineDescription) {
    return parseLaunch(pipelineDescription, null);
  }

  public static Element parseLaunch(String[] pipelineDescription, List<GError> errors) {
    Pointer[] err = {null};
    Element pipeline = GSTPARSE_API.gst_parse_launchv(pipelineDescription, err);
    if (pipeline == null) {
      throw new GstException(extractError(err[0]));
    }
    if (err[0] != null) {
      if (errors != null) {
        errors.add(extractError(err[0]));
      } else {
        LOG.log(Level.WARNING, extractError(err[0]).getMessage());
      }
    }
    return pipeline;
  }

  public static Element parseLaunch(String[] pipelineDescription) {
    return parseLaunch(pipelineDescription, null);
  }

  public static Bin parseBinFromDescription(String binDescription, boolean ghostUnlinkedPads, List<GError> errors) {
    Pointer[] err = {null};
    Bin bin = GSTPARSE_API.gst_parse_bin_from_description(binDescription, ghostUnlinkedPads, err);
    if (bin == null) {
      throw new GstException(extractError(err[0]));
    }
    if (err[0] != null) {
      if (errors != null) {
        errors.add(extractError(err[0]));
      } else {
        LOG.log(Level.WARNING, extractError(err[0]).getMessage());
      }
    }
    return bin;
  }

  public static Bin parseBinFromDescription(String binDescription, boolean ghostUnlinkedPads) {
    return parseBinFromDescription(binDescription, ghostUnlinkedPads, null);
  }

  private static GError extractError(Pointer errorPtr) {
    GErrorStruct struct = new GErrorStruct(errorPtr);
    struct.read();
    GError err = new GError(struct.getCode(), struct.getMessage());
    GLIB_API.g_error_free(struct);
    return err;
  }

  public static void main() {
    try {
      CountDownLatch latch = quit;
      if (latch != null) {
        latch.await();
      }
    } catch (InterruptedException ex) {
    } finally {
      quit = new CountDownLatch(1);
    }
  }

  public static void invokeLater(final Runnable task) {
    getExecutor().execute(task);
  }

  public static GMainContext getMainContext() {
    return mainContext;
  }

  public static final void init() throws GstException {
    init(Version.BASELINE, "gst1-java-core");
  }

  public static final void init(Version requiredVersion) throws GstException {
    init(requiredVersion, "gst1-java-core");
  }

  public static final synchronized String[] init(String progname, String... args) throws GstException {
    return init(Version.BASELINE, progname, args);
  }

  public static final synchronized String[] init(Version requestedVersion, String progname, String... args) throws GstException {
    if (CHECK_VERSIONS) {
      Version availableVersion = getVersion();
      if (requestedVersion.getMajor() != 1 || availableVersion.getMajor() != 1) {
        throw new GstException("gst1-java-core only supports GStreamer 1.x");
      }
      if (requestedVersion.getMinor() < 8) {
        requestedVersion = new Version(1, 8);
      }
      if (!availableVersion.checkSatisfies(requestedVersion)) {
        throw new GstException(String.format("The requested version of GStreamer is not available\n"
                + "Requested : %s\n"
                + "Available : %s\n",
            requestedVersion, availableVersion));
      }
    }
    if (INIT_COUNT.getAndIncrement() > 0) {
      if (CHECK_VERSIONS) {
        if (requestedVersion.getMinor() > minorVersion) {
          minorVersion = (int) requestedVersion.getMinor();
        }
      }
      return args;
    }
    NativeArgs argv = new NativeArgs(progname, args);
    Pointer[] error = {null};
    if (!GST_API.gst_init_check(argv.argcRef, argv.argvRef, error)) {
      INIT_COUNT.decrementAndGet();
      throw new GstException(extractError(error[0]));
    }
    LOG.fine("after gst_init, argc=" + argv.argcRef.getValue());
    Version runningVersion = getVersion();
    if (runningVersion.getMajor() != 1) {
      LOG.warning("gst1-java-core only supports GStreamer 1.x");
    }
    if (useDefaultContext) {
      mainContext = GMainContext.getDefaultContext();
      executorService = new MainContextExecutorService(mainContext);
    } else {
      mainContext = new GMainContext();
      executorService = Executors.newSingleThreadScheduledExecutor(threadFactory);
    }
    quit = new CountDownLatch(1);
    loadAllClasses();
    if (CHECK_VERSIONS) {
      minorVersion = requestedVersion.getMinor();
    }
    return argv.toStringArray();
  }

  public static final synchronized void deinit() {
    if (INIT_COUNT.decrementAndGet() > 0) {
      return;
    }
    for (Object task : shutdownTasks.toArray()) {
      ((Runnable) task).run();
    }
    executorService.shutdown();
    quit();
    try {
      if (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
        executorService.shutdownNow();
      }
    } catch (InterruptedException ex) {
    }
    mainContext = null;
    System.gc();
    GST_API.gst_deinit();
  }

  static void addStaticShutdownTask(Runnable task) {
    shutdownTasks.add(task);
  }

  public static void setUseDefaultContext(boolean useDefault) {
    useDefaultContext = useDefault;
  }

  public static void checkVersion(int major, int minor) {
    if (CHECK_VERSIONS && (major != 1 || minor > minorVersion)) {
      throw new GstException("Not supported by requested GStreamer version");
    }
  }

  public static boolean testVersion(int major, int minor) {
    if (CHECK_VERSIONS && (major != 1 || minor > minorVersion)) {
      return false;
    }
    return true;
  }

  private static final ThreadFactory threadFactory = new ThreadFactory() {
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable task) {
      final String name = "gstreamer service thread " + counter.incrementAndGet();
      Thread t = new Thread(task, name);
      t.setDaemon(true);
      t.setPriority(Thread.NORM_PRIORITY);
      return t;
    }
  };

  private static synchronized void loadAllClasses() {
    Stream.of(new GLib.Types(), new Types(), new Event.Types(), new Message.Types(), new Query.Types(), new Controllers(), new Elements(), new WebRTC.Types(), new Video.Types()).flatMap(NativeObject.TypeProvider::types).forEachOrdered(GstTypes::register);
    if (!DISABLE_EXTERNAL) {
      try {
        ServiceLoader<NativeObject.TypeProvider> extProviders = ServiceLoader.load(NativeObject.TypeProvider.class);
        extProviders.iterator().forEachRemaining(prov -> prov.types().forEachOrdered(GstTypes::register));
      } catch (Throwable t) {
        LOG.log(Level.SEVERE, "Error during external types registration", t);
      }
    }
  }

  public static class Types implements NativeObject.TypeProvider {
    @SuppressWarnings("deprecation")
    @Override
    public Stream<NativeObject.TypeRegistration<?>> types() {
      return Stream.of(registration(Bin.class, Bin.GTYPE_NAME, Bin::new), registration(Buffer.class, Buffer.GTYPE_NAME, Buffer::new), registration(BufferPool.class, BufferPool.GTYPE_NAME, BufferPool::new), registration(Bus.class, Bus.GTYPE_NAME, Bus::new), registration(Caps.class, Caps.GTYPE_NAME, Caps::new), registration(Clock.class, Clock.GTYPE_NAME, Clock::new), registration(Context.class, Context.GTYPE_NAME, Context::new), registration(DateTime.class, DateTime.GTYPE_NAME, DateTime::new), registration(Element.class, Element.GTYPE_NAME, Element::new), registration(ElementFactory.class, ElementFactory.GTYPE_NAME, ElementFactory::new), registration(GhostPad.class, GhostPad.GTYPE_NAME, GhostPad::new), registration(Pad.class, Pad.GTYPE_NAME, Pad::new), registration(PadTemplate.class, PadTemplate.GTYPE_NAME, PadTemplate::new), registration(Pipeline.class, Pipeline.GTYPE_NAME, Pipeline::new), registration(Plugin.class, Plugin.GTYPE_NAME, Plugin::new), registration(PluginFeature.class, PluginFeature.GTYPE_NAME, PluginFeature::new), registration(Promise.class, Promise.GTYPE_NAME, Promise::new), registration(Registry.class, Registry.GTYPE_NAME, Registry::new), registration(SDPMessage.class, SDPMessage.GTYPE_NAME, SDPMessage::new), registration(Sample.class, Sample.GTYPE_NAME, Sample::new), registration(Structure.class, Structure.GTYPE_NAME, Structure::new), registration(TagList.class, TagList.GTYPE_NAME, TagList::new));
    }
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public static @interface Since {
    public int major() default 1;

    public int minor();
  }
}
