package org.freedesktop.gstreamer.message;

import static org.freedesktop.gstreamer.lowlevel.GstMessageAPI.GSTMESSAGE_API;

import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.glib.Natives;

public class BufferingMessage extends Message {
  BufferingMessage(Initializer init) {
    super(init);
  }

  public BufferingMessage(GstObject src, int percent) {
    this(Natives.initializer(GSTMESSAGE_API.ptr_gst_message_new_buffering(src, percent)));
  }

  public int getPercent() {
    int[] percent = {0};
    GSTMESSAGE_API.gst_message_parse_buffering(this, percent);
    return percent[0];
  }
}
