package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import org.freedesktop.gstreamer.glib.Natives;

public class LatencyEvent extends Event {
  LatencyEvent(Initializer init) {
    super(init);
  }

  public LatencyEvent(long latency) {
    super(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_latency(latency)));
  }

  public long getLatency() {
    long[] latency = new long[1];
    GSTEVENT_API.gst_event_parse_latency(this, latency);
    return latency[0];
  }
}
