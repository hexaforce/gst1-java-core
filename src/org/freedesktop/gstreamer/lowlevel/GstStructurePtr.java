package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class GstStructurePtr extends GTypedPtr {
  public GstStructurePtr() {}

  public GstStructurePtr(Pointer ptr) {
    super(ptr);
  }

  @Override
  public GType getGType() {
    if (Native.SIZE_T_SIZE == 8) {
      return GType.valueOf(getPointer().getLong(0));
    } else if (Native.SIZE_T_SIZE == 4) {
      return GType.valueOf(((long) getPointer().getInt(0)) & 0xffffffffL);
    } else {
      throw new IllegalStateException("SIZE_T size not supported: " + Native.SIZE_T_SIZE);
    }
  }
}
