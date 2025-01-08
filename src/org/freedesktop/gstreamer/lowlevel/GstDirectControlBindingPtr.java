package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;

public class GstDirectControlBindingPtr extends GstControlBindingPtr {
  public GstDirectControlBindingPtr() {}

  public GstDirectControlBindingPtr(Pointer ptr) {
    super(ptr);
  }
}
