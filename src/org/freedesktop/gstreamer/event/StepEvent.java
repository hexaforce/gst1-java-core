package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.glib.Natives;

public class StepEvent extends Event {
  StepEvent(Initializer init) {
    super(init);
  }

  public StepEvent(Format format, long amount, double rate, boolean flush, boolean intermediate) {
    super(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_step(format, amount, rate, flush, intermediate)));
  }
}
