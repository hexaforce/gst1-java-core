package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeEnum;

public enum PadDirection implements NativeEnum<PadDirection> {
  UNKNOWN(0),
  SRC(1),
  SINK(2);
  private final int value;

  private PadDirection(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
