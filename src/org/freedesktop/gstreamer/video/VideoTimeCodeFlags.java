package org.freedesktop.gstreamer.video;

import org.freedesktop.gstreamer.glib.NativeFlags;

public enum VideoTimeCodeFlags implements NativeFlags<VideoTimeCodeFlags> {
  GST_VIDEO_TIME_CODE_FLAGS_DROP_FRAME(1),
  GST_VIDEO_TIME_CODE_FLAGS_INTERLACED(2);
  private final int value;

  VideoTimeCodeFlags(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
