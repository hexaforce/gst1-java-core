package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.interfaces.VideoOverlay;
import org.freedesktop.gstreamer.message.Message;

public interface GstVideoOverlayAPI extends Library {
  GstVideoOverlayAPI GSTVIDEOOVERLAY_API = GstNative.load("gstvideo", GstVideoOverlayAPI.class);

  GType gst_video_overlay_get_type();

  void gst_video_overlay_set_window_handle(VideoOverlay overlay, Pointer xwindow_id);

  void gst_video_overlay_got_window_handle(VideoOverlay overlay, NativeLong xwindow_id);

  void gst_video_overlay_prepare_xwindow_id(VideoOverlay overlay);

  void gst_video_overlay_expose(VideoOverlay overlay);

  void gst_video_overlay_handle_events(VideoOverlay overlay, boolean handle_events);

  boolean gst_video_overlay_set_render_rectangle(VideoOverlay overlay, int x, int y, int width, int height);

  boolean gst_is_video_overlay_prepare_window_handle_message(Message message);
}
