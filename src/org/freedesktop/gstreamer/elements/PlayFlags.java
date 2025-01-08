package org.freedesktop.gstreamer.elements;

import org.freedesktop.gstreamer.glib.NativeFlags;

public enum PlayFlags implements NativeFlags<PlayFlags> {
  VIDEO(1 << 0),
  AUDIO(1 << 1),
  TEXT(1 << 2),
  VIS(1 << 3),
  SOFT_VOLUME(1 << 4),
  NATIVE_AUDIO(1 << 5),
  NATIVE_VIDEO(1 << 6),
  DOWNLOAD(1 << 7),
  BUFFERING(1 << 8),
  DEINTERLACE(1 << 9),
  SOFT_COLORBALANCE(1 << 10),
  FORCE_FILTERS(1 << 11),
  FORCE_SW_DECODERS(1 << 12);
  private final int value;

  private PlayFlags(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
