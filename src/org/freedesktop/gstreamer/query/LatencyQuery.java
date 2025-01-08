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
