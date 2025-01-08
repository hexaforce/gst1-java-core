package org.freedesktop.gstreamer.query;

import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstQueryAPI;

public class DurationQuery extends Query {
  DurationQuery(Initializer init) {
    super(init);
  }

  public DurationQuery(Format format) {
    super(Natives.initializer(GstQueryAPI.GSTQUERY_API.ptr_gst_query_new_duration(format)));
  }

  public void setDuration(Format format, long duration) {
    GstQueryAPI.GSTQUERY_API.gst_query_set_duration(this, format, duration);
  }

  public Format getFormat() {
    Format[] fmt = new Format[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_duration(this, fmt, null);
    return fmt[0];
  }

  public long getDuration() {
    long[] duration = new long[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_duration(this, null, duration);
    return duration[0];
  }

  @Override
  public String toString() {
    return String.format("duration: [format=%s, duration=%d]", getFormat(), getDuration());
  }
}
