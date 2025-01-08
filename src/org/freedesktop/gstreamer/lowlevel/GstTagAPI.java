package org.freedesktop.gstreamer.lowlevel;

import org.freedesktop.gstreamer.TagFlag;

public interface GstTagAPI extends com.sun.jna.Library {
  GstTagAPI GSTTAG_API = GstNative.load(GstTagAPI.class);

  boolean gst_tag_exists(String tag);

  GType gst_tag_get_type(String tag);

  String gst_tag_get_nick(String tag);

  String gst_tag_get_description(String tag);

  TagFlag gst_tag_get_flag(String tag);

  boolean gst_tag_is_fixed(String tag);
}
