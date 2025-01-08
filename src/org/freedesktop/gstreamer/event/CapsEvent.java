package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.glib.Natives;

public class CapsEvent extends Event {
  CapsEvent(Initializer init) {
    super(init);
  }

  public CapsEvent(final Caps caps) {
    super(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_caps(caps)));
  }
}
