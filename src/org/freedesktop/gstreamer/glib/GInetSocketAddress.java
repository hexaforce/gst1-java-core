package org.freedesktop.gstreamer.glib;

import static org.freedesktop.gstreamer.lowlevel.GioAPI.GIO_API;

import com.sun.jna.Pointer;

public class GInetSocketAddress extends GSocketAddress {
  public static final String GTYPE_NAME = "GInetSocketAddress";

  public GInetSocketAddress(String address, int port) {
    this(createRawAddress(address, port));
  }

  GInetSocketAddress(Initializer init) {
    super(init);
  }

  public GInetAddress getAddress() {
    return (GInetAddress) get("address");
  }

  public int getPort() {
    return (Integer) get("port");
  }

  private static Initializer createRawAddress(String address, int port) {
    Pointer nativePointer = GIO_API.g_inet_socket_address_new_from_string(address, port);
    if (nativePointer == null) {
      throw new GLibException("Can not create " + GInetSocketAddress.class.getSimpleName() + " for " + address + ":" + port + ", please check that the IP address is valid, with format x.x.x.x");
    }
    return Natives.initializer(nativePointer);
  }
}
