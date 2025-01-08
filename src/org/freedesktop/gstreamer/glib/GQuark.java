package org.freedesktop.gstreamer.glib;

import org.freedesktop.gstreamer.lowlevel.GObjectAPI;

public class GQuark {
  private final int value;

  public GQuark(int value) {
    this.value = value;
  }

  public int intValue() {
    return value;
  }

  @Override
  public String toString() {
    return GObjectAPI.GOBJECT_API.g_quark_to_string(this);
  }

  public static GQuark valueOf(String quark) {
    return GObjectAPI.GOBJECT_API.g_quark_from_string(quark);
  }
}
