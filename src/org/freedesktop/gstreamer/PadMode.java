package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;

public enum PadMode {
  @DefaultEnumValue NONE,
  PUSH,
  PULL;
}
