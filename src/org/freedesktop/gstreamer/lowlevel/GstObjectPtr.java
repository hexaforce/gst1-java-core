package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;

public class GstObjectPtr extends GObjectPtr {
  public GstObjectPtr() {}

  public GstObjectPtr(Pointer ptr) {
    super(ptr);
  }
}
