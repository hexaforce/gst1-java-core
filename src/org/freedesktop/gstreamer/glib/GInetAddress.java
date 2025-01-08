package org.freedesktop.gstreamer.glib;

import static org.freedesktop.gstreamer.lowlevel.GioAPI.GIO_API;

public class GInetAddress extends GObject {
  public static final String GTYPE_NAME = "GInetAddress";

  GInetAddress(Initializer init) {
    super(init);
  }

  public String getAddress() {
    return GIO_API.g_inet_address_to_string(getRawPointer());
  }
}
