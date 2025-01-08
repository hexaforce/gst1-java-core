package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import java.util.EnumSet;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.glib.NativeFlags;
import org.freedesktop.gstreamer.glib.Natives;

public class SeekEvent extends Event {
  SeekEvent(Initializer init) {
    super(init);
  }

  public SeekEvent(double rate, Format format, EnumSet<SeekFlags> flags, SeekType startType, long start, SeekType stopType, long stop) {
    super(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_seek(sanitizeRate(rate), format, NativeFlags.toInt(flags), startType, start, stopType, stop)));
  }

  private static double sanitizeRate(double rate) {
    if (rate == 0d) {
      throw new IllegalArgumentException("Cannot have rate == 0.0");
    }
    return rate;
  }

  public double getRate() {
    double[] rate = {0d};
    GSTEVENT_API.gst_event_parse_seek(this, rate, null, null, null, null, null, null);
    return rate[0];
  }

  public Format getFormat() {
    Format[] format = new Format[1];
    GSTEVENT_API.gst_event_parse_seek(this, null, format, null, null, null, null, null);
    return format[0];
  }

  public EnumSet<SeekFlags> getFlags() {
    int[] flags = {0};
    GSTEVENT_API.gst_event_parse_seek(this, null, null, flags, null, null, null, null);
    return NativeFlags.fromInt(SeekFlags.class, flags[0]);
  }

  public SeekType getStartType() {
    SeekType[] type = new SeekType[1];
    GSTEVENT_API.gst_event_parse_seek(this, null, null, null, type, null, null, null);
    return type[0];
  }

  public long getStart() {
    long[] value = {0};
    GSTEVENT_API.gst_event_parse_seek(this, null, null, null, null, value, null, null);
    return value[0];
  }

  public SeekType getStopType() {
    SeekType[] type = new SeekType[1];
    GSTEVENT_API.gst_event_parse_seek(this, null, null, null, null, null, type, null);
    return type[0];
  }

  public long getStop() {
    long[] value = {0};
    GSTEVENT_API.gst_event_parse_seek(this, null, null, null, null, null, null, value);
    return value[0];
  }
}
