package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GlibAPI.GLIB_API;
import static org.freedesktop.gstreamer.lowlevel.GstBusAPI.GSTBUS_API;
import static org.freedesktop.gstreamer.lowlevel.GstMessageAPI.GSTMESSAGE_API;
import static org.freedesktop.gstreamer.lowlevel.GstMiniObjectAPI.GSTMINIOBJECT_API;

import com.sun.jna.Callback;
import com.sun.jna.CallbackThreadInitializer;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GErrorStruct;
import org.freedesktop.gstreamer.lowlevel.GstBusAPI;
import org.freedesktop.gstreamer.lowlevel.GstBusAPI.BusCallback;
import org.freedesktop.gstreamer.lowlevel.GstBusPtr;
import org.freedesktop.gstreamer.lowlevel.GstMessagePtr;
import org.freedesktop.gstreamer.message.Message;
import org.freedesktop.gstreamer.message.MessageType;

public class Bus extends GstObject {
  public static final String GTYPE_NAME = "GstBus";
  private static final Logger LOG = Logger.getLogger(Bus.class.getName());
  private static final SyncCallback SYNC_CALLBACK = new SyncCallback();
  private volatile BusSyncHandler syncHandler = null;
  private final Object lock = new Object();
  private final List<MessageProxy<?>> messageProxies = new CopyOnWriteArrayList<>();
  private boolean watchAdded = false;

  Bus(Initializer init) {
    super(init);
    GSTBUS_API.gst_bus_set_sync_handler(this, null, null, null);
    GSTBUS_API.gst_bus_set_sync_handler(this, SYNC_CALLBACK, null, null);
  }

  public void setFlushing(boolean flushing) {
    GSTBUS_API.gst_bus_set_flushing(this, flushing ? 1 : 0);
  }

  public void connect(final EOS listener) {
    connect(EOS.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        listener.endOfStream(Natives.objectFor(msg.getSource(), GstObject.class, true, true));
        return true;
      }
    });
  }

  public void disconnect(EOS listener) {
    disconnect(EOS.class, listener);
  }

  public void connect(final ERROR listener) {
    connect(ERROR.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        PointerByReference err = new PointerByReference();
        GSTMESSAGE_API.gst_message_parse_error(msg, err, null);
        GErrorStruct error = new GErrorStruct(err.getValue());
        GstObject source = Natives.objectFor(msg.getSource(), GstObject.class, true, true);
        listener.errorMessage(source, error.getCode(), error.getMessage());
        GLIB_API.g_error_free(err.getValue());
        return true;
      }
    });
  }

  public void disconnect(ERROR listener) {
    disconnect(ERROR.class, listener);
  }

  public void connect(final WARNING listener) {
    connect(WARNING.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        PointerByReference err = new PointerByReference();
        GSTMESSAGE_API.gst_message_parse_warning(msg, err, null);
        GErrorStruct error = new GErrorStruct(err.getValue());
        GstObject source = Natives.objectFor(msg.getSource(), GstObject.class, true, true);
        listener.warningMessage(source, error.getCode(), error.getMessage());
        GLIB_API.g_error_free(err.getValue());
        return true;
      }
    });
  }

  public void disconnect(WARNING listener) {
    disconnect(WARNING.class, listener);
  }

  public void connect(final INFO listener) {
    connect(INFO.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        PointerByReference err = new PointerByReference();
        GSTMESSAGE_API.gst_message_parse_info(msg, err, null);
        GErrorStruct error = new GErrorStruct(err.getValue());
        GstObject source = Natives.objectFor(msg.getSource(), GstObject.class, true, true);
        listener.infoMessage(source, error.getCode(), error.getMessage());
        GLIB_API.g_error_free(err.getValue());
        return true;
      }
    });
  }

  public void disconnect(INFO listener) {
    disconnect(INFO.class, listener);
  }

  public void connect(final STATE_CHANGED listener) {
    connect(STATE_CHANGED.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        IntByReference oldPtr = new IntByReference();
        IntByReference currentPtr = new IntByReference();
        IntByReference pendingPtr = new IntByReference();
        GSTMESSAGE_API.gst_message_parse_state_changed(msg, oldPtr, currentPtr, pendingPtr);
        State old = NativeEnum.fromInt(State.class, oldPtr.getValue());
        State current = NativeEnum.fromInt(State.class, currentPtr.getValue());
        State pending = NativeEnum.fromInt(State.class, pendingPtr.getValue());
        GstObject source = Natives.objectFor(msg.getSource(), GstObject.class, true, true);
        listener.stateChanged(source, old, current, pending);
        return true;
      }
    });
  }

  public void disconnect(STATE_CHANGED listener) {
    disconnect(STATE_CHANGED.class, listener);
  }

  public void connect(final TAG listener) {
    connect(TAG.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        PointerByReference list = new PointerByReference();
        GSTMESSAGE_API.gst_message_parse_tag(msg, list);
        TagList tl = new TagList(Natives.initializer(list.getValue()));
        GstObject source = Natives.objectFor(msg.getSource(), GstObject.class, true, true);
        listener.tagsFound(source, tl);
        return true;
      }
    });
  }

  public void disconnect(TAG listener) {
    disconnect(TAG.class, listener);
  }

  public void connect(final BUFFERING listener) {
    connect(BUFFERING.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        IntByReference percent = new IntByReference();
        GSTMESSAGE_API.gst_message_parse_buffering(msg, percent);
        GstObject source = Natives.objectFor(msg.getSource(), GstObject.class, true, true);
        listener.bufferingData(source, percent.getValue());
        return true;
      }
    });
  }

  public void disconnect(BUFFERING listener) {
    disconnect(BUFFERING.class, listener);
  }

  public void connect(final DURATION_CHANGED listener) {
    connect(DURATION_CHANGED.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        GstObject source = Natives.objectFor(msg.getSource(), GstObject.class, true, true);
        listener.durationChanged(source);
        return true;
      }
    });
  }

  public void disconnect(DURATION_CHANGED listener) {
    disconnect(DURATION_CHANGED.class, listener);
  }

  public void connect(final SEGMENT_START listener) {
    connect(SEGMENT_START.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        IntByReference formatPtr = new IntByReference();
        LongByReference positionPtr = new LongByReference();
        GSTMESSAGE_API.gst_message_parse_segment_start(msg, formatPtr, positionPtr);
        Format format = NativeEnum.fromInt(Format.class, formatPtr.getValue());
        GstObject source = Natives.objectFor(msg.getSource(), GstObject.class, true, true);
        listener.segmentStart(source, format, positionPtr.getValue());
        return true;
      }
    });
  }

  public void disconnect(SEGMENT_START listener) {
    disconnect(SEGMENT_START.class, listener);
  }

  public void connect(final SEGMENT_DONE listener) {
    connect(SEGMENT_DONE.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        IntByReference formatPtr = new IntByReference();
        LongByReference positionPtr = new LongByReference();
        GSTMESSAGE_API.gst_message_parse_segment_done(msg, formatPtr, positionPtr);
        Format format = NativeEnum.fromInt(Format.class, formatPtr.getValue());
        GstObject source = Natives.objectFor(msg.getSource(), GstObject.class, true, true);
        listener.segmentDone(source, format, positionPtr.getValue());
        return true;
      }
    });
  }

  public void disconnect(SEGMENT_DONE listener) {
    disconnect(SEGMENT_DONE.class, listener);
  }

  public void connect(final ASYNC_DONE listener) {
    connect(ASYNC_DONE.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        GstObject source = Natives.objectFor(msg.getSource(), GstObject.class, true, true);
        listener.asyncDone(source);
        return true;
      }
    });
  }

  public void disconnect(ASYNC_DONE listener) {
    disconnect(ASYNC_DONE.class, listener);
  }

  public void connect(final MESSAGE listener) {
    connect(MESSAGE.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        listener.busMessage(Bus.this, Natives.objectFor(msg, Message.class, true, true));
        return true;
      }
    });
  }

  public void connect(String signal, final MESSAGE listener) {
    if (signal.contains("::")) {
      signal = signal.substring(signal.lastIndexOf("::") + 2);
    }
    connect(signal, MESSAGE.class, listener, new BusCallback() {
      public boolean callback(GstBusPtr bus, GstMessagePtr msg, Pointer user_data) {
        listener.busMessage(Bus.this, Natives.objectFor(msg, Message.class, true, true));
        return true;
      }
    });
  }

  public void disconnect(MESSAGE listener) {
    disconnect(MESSAGE.class, listener);
  }

  public boolean post(Message message) {
    return GSTBUS_API.gst_bus_post(this, message);
  }

  public void setSyncHandler(BusSyncHandler handler) {
    syncHandler = handler;
  }

  public void clearSyncHandler() {
    setSyncHandler(null);
  }

  public BusSyncHandler getSyncHandler() {
    return syncHandler;
  }

  private <T> void connect(Class<T> listenerClass, T listener, BusCallback callback) {
    String className = listenerClass.getSimpleName();
    MessageType type;
    if ("MESSAGE".equals(className)) {
      type = MessageType.ANY;
    } else {
      type = MessageType.valueOf(listenerClass.getSimpleName());
    }
    addMessageProxy(type, listenerClass, listener, callback);
  }

  @Override
  public <T> void connect(String signal, Class<T> listenerClass, T listener, final Callback callback) {
    if (listenerClass.getEnclosingClass() != Bus.class) {
      super.connect(signal, listenerClass, listener, callback);
    } else {
      MessageType type;
      if ("message".equals(signal)) {
        type = MessageType.ANY;
      } else {
        type = MessageType.valueOf(signal.toUpperCase(Locale.ROOT).replace('-', '_'));
      }
      addMessageProxy(type, listenerClass, listener, (BusCallback) callback);
    }
  }

  private synchronized<T> void addMessageProxy(MessageType type, Class<T> listenerClass, T listener, BusCallback callback) {
    messageProxies.add(new MessageProxy(type, listenerClass, listener, callback));
    addWatch();
  }

  @Override
  public <T> void disconnect(Class<T> listenerClass, T listener) {
    if (listenerClass.getEnclosingClass() != Bus.class) {
      super.disconnect(listenerClass, listener);
    } else {
      removeMessageProxy(listenerClass, listener);
    }
  }

  private synchronized<T> void removeMessageProxy(Class<T> listenerClass, T listener) {
    messageProxies.removeIf(p -> p.listener == listener);
    if (messageProxies.isEmpty()) {
      removeWatch();
    }
  }

  private void dispatchMessage(GstBusPtr busPtr, GstMessagePtr msgPtr) {
    messageProxies.forEach(p -> {
      try {
        p.busMessage(busPtr, msgPtr);
      } catch (Throwable t) {
        LOG.log(Level.SEVERE, "Exception thrown by bus message handler", t);
      }
    });
    GSTMINIOBJECT_API.gst_mini_object_unref(msgPtr);
  }

  @Override
  public void dispose() {
    removeWatch();
    super.dispose();
  }

  private void addWatch() {
    synchronized (lock) {
      if (!watchAdded) {
        LOG.fine("Add watch");
        GSTBUS_API.gst_bus_add_signal_watch(this);
        watchAdded = true;
      }
    }
  }

  private void removeWatch() {
    synchronized (lock) {
      if (watchAdded) {
        LOG.fine("Remove watch");
        GSTBUS_API.gst_bus_remove_signal_watch(this);
        watchAdded = false;
      }
    }
  }

  public static interface EOS {
    public void endOfStream(GstObject source);
  }

  public static interface ERROR {
    public void errorMessage(GstObject source, int code, String message);
  }

  public static interface WARNING {
    public void warningMessage(GstObject source, int code, String message);
  }

  public static interface INFO {
    public void infoMessage(GstObject source, int code, String message);
  }

  public static interface TAG {
    public void tagsFound(GstObject source, TagList tagList);
  }

  public static interface STATE_CHANGED {
    public void stateChanged(GstObject source, State old, State current, State pending);
  }

  public static interface BUFFERING {
    public void bufferingData(GstObject source, int percent);
  }

  public static interface DURATION_CHANGED {
    public void durationChanged(GstObject source);
  }

  public static interface SEGMENT_START {
    public void segmentStart(GstObject source, Format format, long position);
  }

  public static interface SEGMENT_DONE {
    public void segmentDone(GstObject source, Format format, long position);
  }

  public static interface ASYNC_DONE {
    public void asyncDone(GstObject source);
  }

  public static interface MESSAGE {
    public void busMessage(Bus bus, Message message);
  }

  private static class MessageProxy<T> {
    private final MessageType type;
    private final Class<T> listenerClass;
    private final Object listener;
    private final BusCallback callback;

    MessageProxy(MessageType type, Class<T> listenerClass, T listener, BusCallback callback) {
      this.type = type;
      this.listenerClass = listenerClass;
      this.listener = listener;
      this.callback = callback;
    }

    void busMessage(final GstBusPtr bus, final GstMessagePtr msg) {
      if (type == MessageType.ANY || type.intValue() == msg.getMessageType()) {
        callback.callback(bus, msg, null);
      }
    }
  }

  private static class SyncCallback implements GstBusAPI.BusSyncHandler {
    {
      Native.setCallbackThreadInitializer(this, new CallbackThreadInitializer(true, Boolean.getBoolean("glib.detachCallbackThreads"), "GstBus"));
    }

    @Override
    public BusSyncReply callback(final GstBusPtr busPtr, final GstMessagePtr msgPtr, Pointer userData) {
      Bus bus = Natives.objectFor(busPtr, Bus.class, true, true);
      BusSyncHandler syncHandler = bus.syncHandler;
      if (syncHandler != null) {
        Message msg = Natives.objectFor(msgPtr, Message.class, true, true);
        BusSyncReply reply = syncHandler.syncMessage(msg);
        if (reply != BusSyncReply.DROP) {
          Gst.getExecutor().execute(() -> bus.dispatchMessage(busPtr, msgPtr));
        } else {
          GSTMINIOBJECT_API.gst_mini_object_unref(msgPtr);
        }
      } else {
        Gst.getExecutor().execute(() -> bus.dispatchMessage(busPtr, msgPtr));
      }
      return BusSyncReply.DROP;
    }
  }
}
