package org.freedesktop.gstreamer.webrtc;

import static org.freedesktop.gstreamer.glib.Natives.registration;

import java.util.stream.Stream;
import org.freedesktop.gstreamer.glib.NativeObject;

public class WebRTC {
  private WebRTC() {}

  public static class Types implements NativeObject.TypeProvider {
    @Override
    public Stream<NativeObject.TypeRegistration<?>> types() {
      return Stream.of(registration(WebRTCSessionDescription.class, WebRTCSessionDescription.GTYPE_NAME, WebRTCSessionDescription::new), registration(WebRTCBin.class, WebRTCBin.GTYPE_NAME, WebRTCBin::new));
    }
  }
}
