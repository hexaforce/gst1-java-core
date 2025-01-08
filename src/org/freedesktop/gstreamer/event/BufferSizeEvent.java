package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.glib.Natives;

public class BufferSizeEvent extends Event {
  BufferSizeEvent(Initializer init) {
    super(init);
  }

  public BufferSizeEvent(Format format, long minsize, long maxsize, boolean async) {
    super(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_buffer_size(format, minsize, maxsize, async)));
  }

  public Format getFormat() {
    Format[] format = new Format[1];
    GSTEVENT_API.gst_event_parse_buffer_size(this, format, null, null, null);
    return format[0];
  }

  public long getMinimumSize() {
    long[] size = {0};
    GSTEVENT_API.gst_event_parse_buffer_size(this, null, size, null, null);
    return size[0];
  }

  public long getMaximumSize() {
    long[] size = {0};
    GSTEVENT_API.gst_event_parse_buffer_size(this, null, null, size, null);
    return size[0];
  }

  public boolean isAsync() {
    boolean[] async = {false};
    GSTEVENT_API.gst_event_parse_buffer_size(this, null, null, null, async);
    return async[0];
  }
}
