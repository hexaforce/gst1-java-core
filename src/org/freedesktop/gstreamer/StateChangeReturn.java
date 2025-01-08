package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeEnum;

public enum StateChangeReturn implements NativeEnum<StateChangeReturn> {
  FAILURE(0),
  SUCCESS(1),
  ASYNC(2),
  NO_PREROLL(3);
  private final int value;

  private StateChangeReturn(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
