package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;

public abstract class GTypedPtr extends GPointer {
  public GTypedPtr() {}

  public GTypedPtr(Pointer ptr) {
    super(ptr);
  }

  public abstract GType getGType();
}
