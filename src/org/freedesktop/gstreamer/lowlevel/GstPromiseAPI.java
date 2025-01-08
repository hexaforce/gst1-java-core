package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.Promise;
import org.freedesktop.gstreamer.PromiseResult;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;
import org.freedesktop.gstreamer.lowlevel.GstMiniObjectAPI.MiniObjectStruct;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;
import org.freedesktop.gstreamer.lowlevel.annotations.Invalidate;

public interface GstPromiseAPI extends com.sun.jna.Library {
  GstPromiseAPI GSTPROMISE_API = GstNative.load(GstPromiseAPI.class);

  public static final class PromiseStruct extends com.sun.jna.Structure {
    public volatile MiniObjectStruct parent;

    public PromiseStruct() {}

    public PromiseStruct(Pointer ptr) {
      useMemory(ptr);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"parent"});
    }
  }

  GType gst_promise_get_type();

  GType gst_static_promise_get_type();

  @CallerOwnsReturn Promise gst_promise_new();

  @CallerOwnsReturn Pointer ptr_gst_promise_new();

  @CallerOwnsReturn Promise gst_promise_new_with_change_func(GstCallback callback, Pointer userData, Pointer destroyNotify);

  @CallerOwnsReturn Pointer ptr_gst_promise_new_with_change_func(GstCallback callback, Pointer userData, Pointer destroyNotify);

  PromiseResult gst_promise_wait(Promise promise);

  void gst_promise_reply(Promise promise, @Invalidate Structure s);

  void gst_promise_interrupt(Promise promise);

  void gst_promise_expire(Promise promise);

  @CallerOwnsReturn Structure gst_promise_get_reply(Promise promise);

  @CallerOwnsReturn Pointer ptr_gst_promise_get_reply(Promise promise);
}
