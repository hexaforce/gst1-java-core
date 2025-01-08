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

import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstQueryAPI;

public class LatencyQuery extends Query {
  LatencyQuery(Initializer init) {
    super(init);
  }

  public LatencyQuery() {
    super(Natives.initializer(GstQueryAPI.GSTQUERY_API.ptr_gst_query_new_latency()));
  }

  public void setLatency(boolean live, long minLatency, long maxLatency) {
    GstQueryAPI.GSTQUERY_API.gst_query_set_latency(this, live, minLatency, maxLatency);
  }

  public boolean isLive() {
    boolean[] live = new boolean[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_latency(this, live, null, null);
    return live[0];
  }

  public long getMinimumLatency() {
    long[] latency = new long[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_latency(this, null, latency, null);
    return latency[0];
  }

  public long getMaximumLatency() {
    long[] latency = new long[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_latency(this, null, null, latency);
    return latency[0];
  }

  @Override
  public String toString() {
    return String.format("latency:[live=%b, min=%s, max=%s]", isLive(), getMinimumLatency(), getMaximumLatency());
  }
}
