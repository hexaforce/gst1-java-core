package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import org.freedesktop.gstreamer.glib.Natives;

public class FlushStartEvent extends Event {
  FlushStartEvent(Initializer init) {
    super(init);
  }

  public FlushStartEvent() {
    super(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_flush_start()));
  }
}
