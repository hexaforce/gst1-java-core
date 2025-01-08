package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;

public enum SDPResult implements NativeEnum<SDPResult> {
  OK(0),
  @DefaultEnumValue EINVAL(-1);
  private final int value;

  SDPResult(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
