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

public class SeekingQuery extends Query {
  SeekingQuery(Initializer init) {
    super(init);
  }

  public SeekingQuery(Format format) {
    this(Natives.initializer(GstQueryAPI.GSTQUERY_API.ptr_gst_query_new_seeking(format)));
  }

  public void setSeeking(Format format, boolean seekable, long start, long end) {
    GstQueryAPI.GSTQUERY_API.gst_query_set_seeking(this, format, seekable, start, end);
  }

  public boolean isSeekable() {
    boolean[] value = {false};
    GstQueryAPI.GSTQUERY_API.gst_query_parse_seeking(this, null, value, null, null);
    return value[0];
  }

  public Format getFormat() {
    Format[] value = {Format.UNDEFINED};
    GstQueryAPI.GSTQUERY_API.gst_query_parse_seeking(this, value, null, null, null);
    return value[0];
  }

  public long getStart() {
    long[] value = {0};
    GstQueryAPI.GSTQUERY_API.gst_query_parse_seeking(this, null, null, value, null);
    return value[0];
  }

  public long getEnd() {
    long[] value = {0};
    GstQueryAPI.GSTQUERY_API.gst_query_parse_seeking(this, null, null, null, value);
    return value[0];
  }
}
