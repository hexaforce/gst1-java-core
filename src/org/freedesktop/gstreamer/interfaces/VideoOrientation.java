package org.freedesktop.gstreamer.interfaces;

import static org.freedesktop.gstreamer.lowlevel.GstVideoOrientationAPI.GSTVIDEOORIENTATION_API;

import org.freedesktop.gstreamer.Element;

public class VideoOrientation extends GstInterface {
  public static final VideoOrientation wrap(Element element) {
    return new VideoOrientation(element);
  }

  private VideoOrientation(Element element) {
    super(element, GSTVIDEOORIENTATION_API.gst_video_orientation_get_type());
  }

  public boolean setHflip(boolean flip) {
    return GSTVIDEOORIENTATION_API.gst_video_orientation_set_hflip(this, flip);
  }

  public boolean setVflip(boolean flip) {
    return GSTVIDEOORIENTATION_API.gst_video_orientation_set_vflip(this, flip);
  }

  public boolean setHcenter(int center) {
    return GSTVIDEOORIENTATION_API.gst_video_orientation_set_hcenter(this, center);
  }

  public boolean setVcenter(int center) {
    return GSTVIDEOORIENTATION_API.gst_video_orientation_set_vcenter(this, center);
  }
}
