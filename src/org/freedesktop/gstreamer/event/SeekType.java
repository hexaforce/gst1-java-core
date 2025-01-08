package org.freedesktop.gstreamer.event;

import org.freedesktop.gstreamer.glib.NativeEnum;

public enum SeekType implements NativeEnum<SeekType> {
  NONE(0),
  SET(1),
  END(2);
  private final int value;

  private SeekType(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
