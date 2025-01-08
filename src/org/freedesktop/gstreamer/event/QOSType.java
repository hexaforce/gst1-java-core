package org.freedesktop.gstreamer.event;

import org.freedesktop.gstreamer.glib.NativeEnum;

public enum QOSType implements NativeEnum<QOSType> {
  OVERFLOW(0),
  UNDERFLOW(1),
  THROTTLE(2);
  private final int value;

  private QOSType(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
