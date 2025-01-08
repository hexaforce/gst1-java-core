package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.ptr.LongByReference;
import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.FlowReturn;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.elements.AppSink;
import org.freedesktop.gstreamer.elements.AppSrc;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;
import org.freedesktop.gstreamer.lowlevel.annotations.Invalidate;

public interface AppAPI extends com.sun.jna.Library {
  AppAPI APP_API = GstNative.load("gstapp", AppAPI.class);

  GType gst_app_src_get_type();

  void gst_app_src_set_caps(AppSrc appsrc, Caps caps);

  @CallerOwnsReturn Caps gst_app_src_get_caps(AppSrc appsrc);

  void gst_app_src_set_size(AppSrc appsrc, long size);

  long gst_app_src_get_size(AppSrc appsrc);

  void gst_app_src_set_stream_type(AppSrc appsrc, AppSrc.StreamType type);

  AppSrc.StreamType gst_app_src_get_stream_type(AppSrc appsrc);

  void gst_app_src_set_max_bytes(AppSrc appsrc, long max);

  long gst_app_src_get_max_bytes(AppSrc appsrc);

  void gst_app_src_set_latency(AppSrc appsrc, long min, long max);

  void gst_app_src_get_latency(AppSrc appsrc, LongByReference min, LongByReference max);

  void gst_app_src_flush_queued(AppSrc appsrc);

  FlowReturn gst_app_src_push_buffer(AppSrc appsrc, @Invalidate Buffer buffer);

  FlowReturn gst_app_src_end_of_stream(AppSrc appsrc);

  GType gst_app_sink_get_type();

  void gst_app_sink_set_caps(AppSink appsink, Caps caps);

  @CallerOwnsReturn Caps gst_app_sink_get_caps(AppSink appsink);

  boolean gst_app_sink_is_eos(AppSink appsink);

  @CallerOwnsReturn Sample gst_app_sink_pull_preroll(AppSink appsink);

  @CallerOwnsReturn Sample gst_app_sink_pull_sample(AppSink appsink);
}
