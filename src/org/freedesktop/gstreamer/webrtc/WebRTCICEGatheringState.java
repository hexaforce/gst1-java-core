package org.freedesktop.gstreamer.webrtc;

import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.NativeEnum;

@Gst.Since(minor = 14)
public enum WebRTCICEGatheringState implements NativeEnum<WebRTCICEGatheringState> {
  NEW(0),
  GATHERING(1),
  COMPLETE(2);
  private final int value;

  private WebRTCICEGatheringState(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return this.value;
  }
}
