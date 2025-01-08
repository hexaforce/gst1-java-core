package org.freedesktop.gstreamer.video;

import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Meta;
import org.freedesktop.gstreamer.lowlevel.GstVideoAPI.GstVideoTimeCodeMetaStruct;

@Gst.Since(minor = 10)
public class VideoTimeCodeMeta extends Meta {
  public static final API<VideoTimeCodeMeta> API = new API<>(VideoTimeCodeMeta.class, "GstVideoTimeCodeMetaAPI");
  public static final String GTYPE_NAME = "GstVideoTimeCodeMeta";
  private final VideoTimeCode timeCode;

  VideoTimeCodeMeta(Initializer init) {
    super(init);
    GstVideoTimeCodeMetaStruct metaStruct = new GstVideoTimeCodeMetaStruct(init.ptr.getPointer());
    timeCode = new VideoTimeCode(metaStruct.tc);
  }

  public VideoTimeCode getTimeCode() {
    return timeCode;
  }

  @Override
  public void disown() {
    timeCode.disown();
    super.disown();
  }
}
