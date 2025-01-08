package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;

public enum TagMergeMode implements NativeEnum<TagMergeMode> {
  @DefaultEnumValue UNDEFINED(0),
  REPLACE_ALL(1),
  REPLACE(2),
  APPEND(3),
  PREPEND(4),
  KEEP(5),
  KEEP_ALL(6);
  private final int value;

  private TagMergeMode(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
