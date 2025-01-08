package org.freedesktop.gstreamer.message;

import static org.freedesktop.gstreamer.lowlevel.GlibAPI.GLIB_API;

import org.freedesktop.gstreamer.lowlevel.GstAPI;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GErrorStruct;

abstract class GErrorMessage extends Message {
  GErrorMessage(Initializer init) {
    super(init);
  }

  abstract GstAPI.GErrorStruct parseMessage();

  public int getCode() {
    GErrorStruct err = parseMessage();
    if (err == null) {
      throw new NullPointerException("Could not parse message");
    }
    int code = err.code;
    GLIB_API.g_error_free(err);
    return code;
  }

  public String getMessage() {
    GErrorStruct err = parseMessage();
    if (err == null) {
      throw new NullPointerException("Could not parse message");
    }
    String message = err.getMessage();
    GLIB_API.g_error_free(err);
    return message;
  }
}
