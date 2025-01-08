package org.freedesktop.gstreamer.message;

import static org.freedesktop.gstreamer.lowlevel.GstMessageAPI.GSTMESSAGE_API;

import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.glib.Natives;

public class SegmentDoneMessage extends Message {
  SegmentDoneMessage(Initializer init) {
    super(init);
  }

  public SegmentDoneMessage(GstObject src, Format format, long position) {
    this(Natives.initializer(GSTMESSAGE_API.ptr_gst_message_new_segment_done(src, format, position)));
  }

  public Format getFormat() {
    Format[] format = new Format[1];
    GSTMESSAGE_API.gst_message_parse_segment_done(this, format, null);
    return format[0];
  }

  public long getPosition() {
    long[] position = {0};
    GSTMESSAGE_API.gst_message_parse_segment_done(this, null, position);
    return position[0];
  }
}
