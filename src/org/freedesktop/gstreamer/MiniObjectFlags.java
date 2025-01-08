package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeFlags;

public enum MiniObjectFlags implements NativeFlags<MiniObjectFlags> {
  LOCKABLE(1 << 0),
  LOCK_READONLY(1 << 1),
  MAY_BE_LEAKED(1 << 2),
  LAST(1 << 4);
  private final int value;

  private MiniObjectFlags(int value) {
    this.value = value;
  }

  public int intValue() {
    return value;
  }
}
