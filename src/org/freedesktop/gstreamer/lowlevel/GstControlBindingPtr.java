package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;

public class GstControlBindingPtr extends GstObjectPtr {
  public GstControlBindingPtr() {}

  public GstControlBindingPtr(Pointer ptr) {
    super(ptr);
  }
}
