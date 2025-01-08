/*
 * This file is part of gstreamer-java.
 * 
 * This code is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License version 3 only, as published by the Free Software Foundation.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License version 3 for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3 along with this work.
 * If not, see <http://www.gnu.org/licenses/>.
 */

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
