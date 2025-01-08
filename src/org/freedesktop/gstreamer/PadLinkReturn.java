package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;

public enum PadLinkReturn implements NativeEnum<PadLinkReturn> {
  OK(0),
  WRONG_HIERARCHY(-1),
  WAS_LINKED(-2),
  WRONG_DIRECTION(-3),
  NOFORMAT(-4),
  NOSCHED(-5),
  @DefaultEnumValue REFUSED(-6);
  private final int value;

  PadLinkReturn(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
