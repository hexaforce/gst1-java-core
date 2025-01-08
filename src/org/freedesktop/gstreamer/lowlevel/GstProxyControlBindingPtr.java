package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;

public class GstProxyControlBindingPtr extends GstControlBindingPtr {
  public GstProxyControlBindingPtr() {}

  public GstProxyControlBindingPtr(Pointer ptr) {
    super(ptr);
  }
}
