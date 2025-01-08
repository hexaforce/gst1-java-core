package org.freedesktop.gstreamer.query;

import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstQueryAPI;

public class ConvertQuery extends Query {
  ConvertQuery(Initializer init) {
    super(init);
  }

  public ConvertQuery(Format srcFormat, long value, Format destFormat) {
    this(Natives.initializer(GstQueryAPI.GSTQUERY_API.ptr_gst_query_new_convert(srcFormat, value, destFormat)));
  }

  public void setConvert(Format srcFormat, long srcValue, Format dstFormat, long dstValue) {
    GstQueryAPI.GSTQUERY_API.gst_query_set_convert(this, srcFormat, srcValue, dstFormat, dstValue);
  }

  public Format getSourceFormat() {
    Format[] fmt = new Format[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_convert(this, fmt, null, null, null);
    return fmt[0];
  }

  public Format getDestinationFormat() {
    Format[] fmt = new Format[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_convert(this, null, null, fmt, null);
    return fmt[0];
  }

  public long getSourceValue() {
    long[] value = new long[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_convert(this, null, value, null, null);
    return value[0];
  }

  public long getDestinationValue() {
    long[] value = new long[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_convert(this, null, null, null, value);
    return value[0];
  }
}
