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

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstAPI;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstSegmentStruct;

public class SegmentEvent extends Event {
  SegmentEvent(Initializer init) {
    super(init);
  }

  SegmentEvent(GstSegmentStruct segment) {
    this(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_segment(segment)));
  }

  GstAPI.GstSegmentStruct getSegment() {
    Pointer[] segmentPointer = new Pointer[1];
    GSTEVENT_API.gst_event_parse_segment(this, segmentPointer);
    GstSegmentStruct result = new GstAPI.GstSegmentStruct(segmentPointer[0]);
    result.read();
    return result;
  }
}
