package org.freedesktop.gstreamer.message;

import static org.freedesktop.gstreamer.lowlevel.GstMessageAPI.GSTMESSAGE_API;

import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.glib.Natives;

public class EOSMessage extends Message {
  EOSMessage(Initializer init) {
    super(init);
  }

  public EOSMessage(GstObject src) {
    this(Natives.initializer(GSTMESSAGE_API.ptr_gst_message_new_eos(src)));
  }
}
