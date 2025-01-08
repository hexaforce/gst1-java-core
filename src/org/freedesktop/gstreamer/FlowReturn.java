package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;

public enum FlowReturn implements NativeEnum<FlowReturn> {
  @DefaultEnumValue OK(0),
  NOT_LINKED(-1),
  FLUSHING(-2),
  EOS(-3),
  NOT_NEGOTIATED(-4),
  ERROR(-5),
  NOT_SUPPORTED(-6);
  private final int value;

  FlowReturn(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
