package org.freedesktop.gstreamer.event;

import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;

public enum EventType implements NativeEnum<EventType> {
  @DefaultEnumValue UNKNOWN(0, 0),
  FLUSH_START(10, Flags.BOTH),
  FLUSH_STOP(20, Flags.BOTH | Flags.SERIALIZED),
  STREAM_START(40, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY),
  CAPS(50, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY),
  SEGMENT(70, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY),
  @Gst.Since(minor = 10) STREAM_COLLECTION(75, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY | Flags.STICKY_MULTI),
  TAG(80, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY | Flags.STICKY_MULTI),
  BUFFERSIZE(90, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY),
  SINK_MESSAGE(100, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY | Flags.STICKY_MULTI),
  @Gst.Since(minor = 10) STREAM_GROUP_DONE(105, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY),
  EOS(110, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY),
  TOC(120, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY | Flags.STICKY_MULTI),
  PROTECTION(130, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY | Flags.STICKY_MULTI),
  SEGMENT_DONE(150, Flags.DOWNSTREAM | Flags.SERIALIZED),
  GAP(160, Flags.DOWNSTREAM | Flags.SERIALIZED),
  QOS(190, Flags.UPSTREAM),
  SEEK(200, Flags.UPSTREAM),
  NAVIGATION(210, Flags.UPSTREAM),
  LATENCY(220, Flags.UPSTREAM),
  STEP(230, Flags.UPSTREAM),
  RECONFIGURE(240, Flags.UPSTREAM),
  TOC_SELECT(250, Flags.UPSTREAM),
  @Gst.Since(minor = 10) SELECT_STREAMS(260, Flags.UPSTREAM),
  CUSTOM_UPSTREAM(270, Flags.UPSTREAM),
  CUSTOM_DOWNSTREAM(280, Flags.DOWNSTREAM | Flags.SERIALIZED),
  CUSTOM_DOWNSTREAM_OOB(290, Flags.DOWNSTREAM),
  CUSTOM_DOWNSTREAM_STICKY(300, Flags.DOWNSTREAM | Flags.SERIALIZED | Flags.STICKY | Flags.STICKY_MULTI),
  CUSTOM_BOTH(310, Flags.BOTH | Flags.SERIALIZED),
  CUSTOM_BOTH_OOB(320, Flags.BOTH);
  private static final int SHIFT = 8;
  private final int value;

  private EventType(int num, int flags) {
    this.value = (num << SHIFT) | flags;
  }

  @Override
  public int intValue() {
    return value;
  }

  private static final class Flags {
    public static final int UPSTREAM = 1 << 0;
    public static final int DOWNSTREAM = 1 << 1;
    public static final int SERIALIZED = 1 << 2;
    public static final int STICKY = 1 << 3;
    public static final int STICKY_MULTI = 1 << 4;
    public static final int BOTH = UPSTREAM | DOWNSTREAM;
  }
}
