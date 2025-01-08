package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.GError;
import org.freedesktop.gstreamer.glib.GLibException;

public class GstException extends GLibException {
  private static final long serialVersionUID = -7413580400835548033L;

  public GstException() {}

  public GstException(String msg) {
    super(msg);
  }

  public GstException(GError error) {
    super(error);
  }
}
