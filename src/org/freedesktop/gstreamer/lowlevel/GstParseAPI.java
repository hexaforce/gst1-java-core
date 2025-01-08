package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.Bin;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

public interface GstParseAPI extends com.sun.jna.Library {
  GstParseAPI GSTPARSE_API = GstNative.load(GstParseAPI.class);

  @CallerOwnsReturn Element gst_parse_launch(String pipeline_description, Pointer[] error);

  @CallerOwnsReturn Element gst_parse_launchv(String[] pipeline_description, Pointer[] error);

  @CallerOwnsReturn Element gst_parse_launch(String pipeline_description, GstAPI.GErrorStruct[] error);

  @CallerOwnsReturn Element gst_parse_launchv(String[] pipeline_description, GstAPI.GErrorStruct[] error);

  @CallerOwnsReturn Bin gst_parse_bin_from_description(String bin_description, boolean ghost_unlinked_pads, Pointer[] error);
}
