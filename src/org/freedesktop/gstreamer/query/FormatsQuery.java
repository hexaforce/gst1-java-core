package org.freedesktop.gstreamer.query;

import java.util.AbstractList;
import java.util.List;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstQueryAPI;

public class FormatsQuery extends Query {
  public FormatsQuery() {
    this(Natives.initializer(GstQueryAPI.GSTQUERY_API.ptr_gst_query_new_formats()));
  }

  FormatsQuery(Initializer init) {
    super(init);
  }

  public void setFormats(Format... formats) {
    GstQueryAPI.GSTQUERY_API.gst_query_set_formats(this, formats.length, formats);
  }

  public int getCount() {
    int[] count = {0};
    GstQueryAPI.GSTQUERY_API.gst_query_parse_n_formats(this, count);
    return count[0];
  }

  public Format getFormat(int index) {
    Format[] fmt = new Format[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_nth_format(this, index, fmt);
    return fmt[0];
  }

  public List<Format> getFormats() {
    final int count = getCount();
    return new AbstractList<Format>() {
      @Override
      public Format get(int index) {
        return getFormat(index);
      }

      @Override
      public int size() {
        return count;
      }
    };
  }
}
