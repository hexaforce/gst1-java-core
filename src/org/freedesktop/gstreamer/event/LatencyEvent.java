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
