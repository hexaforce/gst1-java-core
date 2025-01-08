package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;

public class GstTriggerControlSourcePtr extends GstTimedValueControlSourcePtr {
  public GstTriggerControlSourcePtr() {}

  public GstTriggerControlSourcePtr(Pointer ptr) {
    super(ptr);
  }
}
