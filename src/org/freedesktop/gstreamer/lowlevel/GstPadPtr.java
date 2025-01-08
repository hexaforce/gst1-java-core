package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;

public class GstPadPtr extends GstObjectPtr {
  public GstPadPtr() {}

  public GstPadPtr(Pointer ptr) {
    super(ptr);
  }
}
