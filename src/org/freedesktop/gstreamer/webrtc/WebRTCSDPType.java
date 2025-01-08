package org.freedesktop.gstreamer.webrtc;

import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.NativeEnum;

@Gst.Since(minor = 12)
public enum WebRTCSDPType implements NativeEnum<WebRTCSDPType> {
  OFFER(1),
  PRANSWER(2),
  ANSWER(3),
  ROLLBACK(4);
  private final int value;

  private WebRTCSDPType(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
