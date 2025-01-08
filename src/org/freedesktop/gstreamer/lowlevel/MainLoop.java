package org.freedesktop.gstreamer.lowlevel;

import static org.freedesktop.gstreamer.lowlevel.GlibAPI.GLIB_API;

import com.sun.jna.Pointer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.GMainContext;
import org.freedesktop.gstreamer.glib.GSource;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.glib.RefCountedObject;

public class MainLoop extends RefCountedObject {
  private static final List<Runnable> bgTasks = new LinkedList<Runnable>();
  private Thread bgThread;

  public MainLoop() {
    this(Natives.initializer(GLIB_API.g_main_loop_new(Gst.getMainContext(), false)));
  }

  public MainLoop(GMainContext ctx) {
    this(Natives.initializer(GLIB_API.g_main_loop_new(ctx, false)));
  }

  public MainLoop(Initializer init) {
    super(new Handle(init.ptr, init.ownsHandle), init.needRef);
  }

  public void quit() {
    invokeLater(new Runnable() {
      public void run() {
        GLIB_API.g_main_loop_quit(MainLoop.this);
      }
    });
  }

  public void run() {
    GLIB_API.g_main_loop_run(this);
  }

  public boolean isRunning() {
    return GLIB_API.g_main_loop_is_running(this);
  }

  public GMainContext getMainContext() {
    return GLIB_API.g_main_loop_get_context(this);
  }

  public void startInBackground() {
    bgThread = new java.lang.Thread(new Runnable() {
      public void run() {
        MainLoop.this.run();
      }
    });
    bgThread.setDaemon(true);
    bgThread.setName("gmainloop");
    bgThread.start();
  }

  public void invokeAndWait(Runnable r) {
    FutureTask<Object> task = new FutureTask<Object>(r, null);
    invokeLater(task);
    try {
      task.get();
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex.getCause());
    } catch (ExecutionException ex) {
      throw new RuntimeException(ex.getCause());
    }
  }

  private static final GlibAPI.GSourceFunc bgCallback = new GlibAPI.GSourceFunc() {
    public boolean callback(Pointer source) {
      List<Runnable> tasks = new ArrayList<Runnable>();
      synchronized (bgTasks) {
        tasks.addAll(bgTasks);
        bgTasks.clear();
      }
      for (Runnable r : tasks) {
        r.run();
      }
      GLIB_API.g_source_unref(source);
      return false;
    }
  };

  public void invokeLater(final Runnable r) {
    synchronized (bgTasks) {
      boolean empty = bgTasks.isEmpty();
      bgTasks.add(r);
      if (empty) {
        GSource source = GLIB_API.g_idle_source_new();
        GLIB_API.g_source_set_callback(source, bgCallback, source, null);
        source.attach(Gst.getMainContext());
        source.disown();
      }
    }
  }

  private static final class Handle extends RefCountedObject.Handle {
    public Handle(GPointer ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {
      GLIB_API.g_main_loop_unref(ptr);
    }

    @Override
    protected void ref() {
      GLIB_API.g_main_loop_ref(getPointer());
    }

    @Override
    protected void unref() {
      GLIB_API.g_main_loop_unref(getPointer());
    }
  }
}
