package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Library;
import org.freedesktop.gstreamer.interfaces.VideoOrientation;

public interface GstVideoOrientationAPI extends Library {
  GstVideoOrientationAPI GSTVIDEOORIENTATION_API = GstNative.load("gstvideo", GstVideoOrientationAPI.class);

  GType gst_video_orientation_get_type();

  boolean gst_video_orientation_set_hflip(VideoOrientation video_orientation, boolean flip);

  boolean gst_video_orientation_set_vflip(VideoOrientation video_orientation, boolean flip);

  boolean gst_video_orientation_set_hcenter(VideoOrientation video_orientation, int center);

  boolean gst_video_orientation_set_vcenter(VideoOrientation video_orientation, int center);
}
