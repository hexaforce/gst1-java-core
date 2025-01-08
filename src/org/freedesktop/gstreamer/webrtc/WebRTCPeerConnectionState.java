package org.freedesktop.gstreamer.webrtc;

import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.NativeEnum;

@Gst.Since(minor = 14)
public enum WebRTCPeerConnectionState implements NativeEnum<WebRTCPeerConnectionState> {
  NEW(0),
  CONNECTING(1),
  CONNECTED(2),
  DISCONNECTED(3),
  FAILED(4),
  CLOSED(5);
  private final int value;

  private WebRTCPeerConnectionState(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
