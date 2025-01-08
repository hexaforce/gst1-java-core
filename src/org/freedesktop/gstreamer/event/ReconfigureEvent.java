package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import org.freedesktop.gstreamer.glib.Natives;

public class ReconfigureEvent extends Event {
  ReconfigureEvent(Initializer init) {
    super(init);
  }

  public ReconfigureEvent() {
    super(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_reconfigure()));
  }
}
