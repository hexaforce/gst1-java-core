package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;

public enum TagFlag implements NativeEnum<TagFlag> {
  @DefaultEnumValue UNDEFINED(0),
  META(1),
  ENCODED(2),
  DECODED(3),
  COUNT(4);
  private final int value;

  private TagFlag(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
