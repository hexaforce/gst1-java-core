package org.freedesktop.gstreamer.glib;

import java.util.Objects;

public class GError {
  private final int code;
  private final String message;

  public GError(int code, String message) {
    this.code = code;
    this.message = Objects.requireNonNull(message);
  }

  public final int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
