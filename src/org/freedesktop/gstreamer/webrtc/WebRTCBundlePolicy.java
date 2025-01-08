package org.freedesktop.gstreamer.webrtc;

import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.NativeEnum;

@Gst.Since(minor = 14)
public enum WebRTCBundlePolicy implements NativeEnum<WebRTCBundlePolicy> {
  NONE(0),
  BALANCED(1),
  MAX_COMPAT(2),
  MAX_BUNDLE(3);

  private final int value;

  private WebRTCBundlePolicy(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
