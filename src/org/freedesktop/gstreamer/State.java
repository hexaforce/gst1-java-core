package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeEnum;

public enum State implements NativeEnum<State> {
  VOID_PENDING(0),
  NULL(1),
  READY(2),
  PAUSED(3),
  PLAYING(4);
  private final int value;

  private State(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
