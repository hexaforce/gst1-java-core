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

public class SegmentQuery extends Query {
  SegmentQuery(Initializer init) {
    super(init);
  }

  public SegmentQuery(Format format) {
    this(Natives.initializer(GstQueryAPI.GSTQUERY_API.ptr_gst_query_new_segment(format)));
  }

  public void setSegment(double rate, Format format, long startValue, long stopValue) {
    GstQueryAPI.GSTQUERY_API.gst_query_set_segment(this, rate, format, startValue, stopValue);
  }

  public double getRate() {
    double[] rate = new double[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_segment(this, rate, null, null, null);
    return rate[0];
  }

  public Format getFormat() {
    Format[] fmt = new Format[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_segment(this, null, fmt, null, null);
    return fmt[0];
  }

  public long getStart() {
    long[] value = new long[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_segment(this, null, null, value, null);
    return value[0];
  }

  public long getEnd() {
    long[] value = new long[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_segment(this, null, null, null, value);
    return value[0];
  }
}
