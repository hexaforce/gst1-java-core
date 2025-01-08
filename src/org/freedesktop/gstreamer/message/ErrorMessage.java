package org.freedesktop.gstreamer.message;

import static org.freedesktop.gstreamer.lowlevel.GstMessageAPI.GSTMESSAGE_API;

import org.freedesktop.gstreamer.lowlevel.GstAPI.GErrorStruct;

public class ErrorMessage extends GErrorMessage {
  ErrorMessage(Initializer init) {
    super(init);
  }

  @Override
  GErrorStruct parseMessage() {
    GErrorStruct[] err = {null};
    GSTMESSAGE_API.gst_message_parse_error(this, err, null);
    return err[0];
  }
}
