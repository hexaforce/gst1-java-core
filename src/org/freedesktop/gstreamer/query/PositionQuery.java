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

public class PositionQuery extends Query {
  PositionQuery(Initializer init) {
    super(init);
  }

  public PositionQuery(Format format) {
    super(Natives.initializer(GstQueryAPI.GSTQUERY_API.ptr_gst_query_new_position(format)));
  }

  public void setPosition(Format format, long position) {
    GstQueryAPI.GSTQUERY_API.gst_query_set_position(this, format, position);
  }

  public Format getFormat() {
    Format[] fmt = new Format[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_position(this, fmt, null);
    return fmt[0];
  }

  public long getPosition() {
    long[] pos = new long[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_position(this, null, pos);
    return pos[0];
  }

  @Override
  public String toString() {
    return String.format("position: [format=%s, position=%d]", getFormat(), getPosition());
  }
}
