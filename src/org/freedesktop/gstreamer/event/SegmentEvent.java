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
