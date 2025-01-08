package org.freedesktop.gstreamer.controller;

import org.freedesktop.gstreamer.glib.NativeEnum;

public enum LFOWaveform implements NativeEnum<LFOWaveform> {
  SINE(0),
  SQUARE(1),
  SAW(2),
  REVERSE_SAW(3),
  TRIANGLE(4);
  private final int value;

  private LFOWaveform(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
