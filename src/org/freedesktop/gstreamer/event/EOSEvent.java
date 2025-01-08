package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import org.freedesktop.gstreamer.glib.Natives;

public class EOSEvent extends Event {
  EOSEvent(Initializer init) {
    super(init);
  }

  public EOSEvent() {
    super(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_eos()));
  }
}
