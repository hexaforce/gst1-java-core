package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.BufferPool;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

public interface GstBufferPoolAPI extends com.sun.jna.Library {
  GstBufferPoolAPI GSTBUFFERPOOL_API = GstNative.load(GstBufferPoolAPI.class);

  GType gst_buffer_pool_get_type();

  @CallerOwnsReturn BufferPool gst_buffer_pool_new();

  Pointer ptr_gst_buffer_pool_new();

  Structure gst_buffer_pool_get_config(BufferPool pool);

  boolean gst_buffer_pool_config_get_params(Structure config, Pointer[] caps, int[] size, int[] min_buffers, int[] max_buffers);

  void gst_buffer_pool_config_set_params(Structure config, Caps caps, int size, int min_buffers, int max_buffers);
}
