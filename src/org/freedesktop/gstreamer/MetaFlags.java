package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeFlags;

enum MetaFlags implements NativeFlags<MetaFlags> {
  GST_META_FLAG_READONLY(1 << 0),
  GST_META_FLAG_POOLED(1 << 1),
  GST_META_FLAG_LOCKED(1 << 2),
  ;
  private final int value;

  MetaFlags(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
