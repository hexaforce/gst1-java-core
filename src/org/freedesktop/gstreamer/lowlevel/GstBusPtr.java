package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;

public class GstBusPtr extends GstObjectPtr {
  public GstBusPtr() {}

  public GstBusPtr(Pointer ptr) {
    super(ptr);
  }
}
