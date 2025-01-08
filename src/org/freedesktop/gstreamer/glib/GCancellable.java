package org.freedesktop.gstreamer.glib;

import static org.freedesktop.gstreamer.lowlevel.GioAPI.GIO_API;

public class GCancellable extends GObject {
  public static final String GTYPE_NAME = "GCancellable";

  public GCancellable() {
    this(Natives.initializer(GIO_API.g_cancellable_new()));
  }

  private GCancellable(Initializer init) {
    super(init);
  }
}
