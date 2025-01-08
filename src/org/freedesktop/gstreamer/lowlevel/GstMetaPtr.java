package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class GstMetaPtr extends GTypedPtr {
  private static final int INFO_OFFSET;
  private static final int IMPL_TYPE_OFFSET;

  static {
    INFO_OFFSET = new GstMetaAPI.GstMetaStruct().infoOffset();
    IMPL_TYPE_OFFSET = new GstMetaAPI.GstMetaInfoStruct().typeOffset();
  }

  public GstMetaPtr() {}

  public GstMetaPtr(Pointer ptr) {
    super(ptr);
  }

  @Override
  public GType getGType() {
    Pointer metaInfo = getPointer().getPointer(INFO_OFFSET);
    if (Native.SIZE_T_SIZE == 8) {
      return GType.valueOf(metaInfo.getLong(IMPL_TYPE_OFFSET));
    } else if (Native.SIZE_T_SIZE == 4) {
      return GType.valueOf(((long) metaInfo.getInt(IMPL_TYPE_OFFSET)) & 0xffffffffL);
    } else {
      throw new IllegalStateException("SIZE_T size not supported: " + Native.SIZE_T_SIZE);
    }
  }

  public GType getAPIGType() {
    Pointer metaInfo = getPointer().getPointer(INFO_OFFSET);
    if (Native.SIZE_T_SIZE == 8) {
      return GType.valueOf(metaInfo.getLong(0));
    } else if (Native.SIZE_T_SIZE == 4) {
      return GType.valueOf(((long) metaInfo.getInt(0)) & 0xffffffffL);
    } else {
      throw new IllegalStateException("SIZE_T size not supported: " + Native.SIZE_T_SIZE);
    }
  }
}
