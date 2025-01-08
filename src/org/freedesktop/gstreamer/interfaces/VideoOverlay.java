package org.freedesktop.gstreamer.interfaces;

import static org.freedesktop.gstreamer.lowlevel.GstVideoOverlayAPI.GSTVIDEOOVERLAY_API;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.message.Message;

public class VideoOverlay extends GstInterface {
  public static VideoOverlay wrap(Element element) {
    return new VideoOverlay(element);
  }

  public static boolean isPrepareWindowHandleMessage(Message message) {
    return GSTVIDEOOVERLAY_API.gst_is_video_overlay_prepare_window_handle_message(message);
  }

  private VideoOverlay(Element element) {
    super(element, GSTVIDEOOVERLAY_API.gst_video_overlay_get_type());
  }

  public void setWindowHandle(long handle) {
    GSTVIDEOOVERLAY_API.gst_video_overlay_set_window_handle(this, new Pointer(handle));
  }

  public void expose() {
    GSTVIDEOOVERLAY_API.gst_video_overlay_expose(this);
  }

  public void handleEvent(boolean handle_events) {
    GSTVIDEOOVERLAY_API.gst_video_overlay_handle_events(this, handle_events);
  }

  public boolean setRenderRectangle(int x, int y, int width, int height) {
    return GSTVIDEOOVERLAY_API.gst_video_overlay_set_render_rectangle(this, x, y, width, height);
  }
}
