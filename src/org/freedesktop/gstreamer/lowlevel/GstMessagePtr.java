package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;

public class GstMessagePtr extends GstMiniObjectPtr {
  private static final int TYPE_OFFSET;
  private static final int SRC_OFFSET;

  static {
    GstMessageAPI.MessageStruct struct = new GstMessageAPI.MessageStruct();
    TYPE_OFFSET = struct.typeOffset();
    SRC_OFFSET = struct.srcOffset();
  }

  public GstMessagePtr() {}

  public GstMessagePtr(Pointer ptr) {
    super(ptr);
  }

  public int getMessageType() {
    return getPointer().getInt(TYPE_OFFSET);
  }

  public GstObjectPtr getSource() {
    Pointer raw = getPointer().getPointer(SRC_OFFSET);
    return raw == null ? null : new GstObjectPtr(raw);
  }
}
