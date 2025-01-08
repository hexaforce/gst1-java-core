package org.freedesktop.gstreamer.controller;

import org.freedesktop.gstreamer.glib.NativeEnum;

public enum InterpolationMode implements NativeEnum<InterpolationMode> {
  NONE(0),
  LINEAR(1),
  CUBIC(2),
  CUBIC_MONOTONIC(3);
  private final int value;

  private InterpolationMode(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
