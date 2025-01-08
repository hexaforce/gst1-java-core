package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class GObjectPtr extends GTypedPtr {
  public GObjectPtr() {}

  public GObjectPtr(Pointer ptr) {
    super(ptr);
  }

  @Override
  public GType getGType() {
    Pointer g_class = getPointer().getPointer(0);
    if (Native.SIZE_T_SIZE == 8) {
      return GType.valueOf(g_class.getLong(0));
    } else if (Native.SIZE_T_SIZE == 4) {
      return GType.valueOf(((long) g_class.getInt(0)) & 0xffffffffL);
    } else {
      throw new IllegalStateException("SIZE_T size not supported: " + Native.SIZE_T_SIZE);
    }
  }
}
