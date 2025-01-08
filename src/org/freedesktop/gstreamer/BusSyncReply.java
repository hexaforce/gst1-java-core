package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.NativeEnum;

public enum BusSyncReply implements NativeEnum<BusSyncReply> {
  DROP(0),
  PASS(1),
  ASYNC(2);
  private final int value;

  BusSyncReply(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
