package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;

public class GstIteratorPtr extends GPointer {
  public GstIteratorPtr() {}

  public GstIteratorPtr(Pointer ptr) {
    super(ptr);
  }
}
