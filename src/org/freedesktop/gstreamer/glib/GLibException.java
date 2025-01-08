package org.freedesktop.gstreamer.glib;

public class GLibException extends RuntimeException {
  public GLibException() {}

  public GLibException(String msg) {
    super(msg);
  }

  public GLibException(GError error) {
    super(error.getMessage());
  }
}
