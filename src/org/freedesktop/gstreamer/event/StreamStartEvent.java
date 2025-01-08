package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import org.freedesktop.gstreamer.glib.Natives;

public class StreamStartEvent extends Event {
  StreamStartEvent(Initializer init) {
    super(init);
  }

  public StreamStartEvent(final String stream_id) {
    super(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_stream_start(stream_id)));
  }
}
