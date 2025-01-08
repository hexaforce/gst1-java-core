package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstSampleAPI.GSTSAMPLE_API;

public class Sample extends MiniObject {
  public static final String GTYPE_NAME = "GstSample";

  Sample(Initializer init) {
    super(init);
  }

  public Caps getCaps() {
    return GSTSAMPLE_API.gst_sample_get_caps(this);
  }

  @Gst.Since(minor = 16)
  public void setCaps(Caps caps) {
    Gst.checkVersion(1, 16);
    GSTSAMPLE_API.gst_sample_set_caps(this, caps);
  }

  public Buffer getBuffer() {
    return GSTSAMPLE_API.gst_sample_get_buffer(this);
  }

  @Gst.Since(minor = 16)
  public void setBuffer(Buffer buffer) {
    Gst.checkVersion(1, 16);
    GSTSAMPLE_API.gst_sample_set_buffer(this, buffer);
  }
}
