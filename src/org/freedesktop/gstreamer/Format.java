package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;

public enum Format implements NativeEnum<Format> {
  @DefaultEnumValue UNDEFINED(0),
  DEFAULT(1),
  BYTES(2),
  TIME(3),
  BUFFERS(4),
  PERCENT(5);
  private final int value;

  Format(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
