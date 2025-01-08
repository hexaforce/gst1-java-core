package org.freedesktop.gstreamer.video;

import static org.freedesktop.gstreamer.glib.Natives.registration;

import java.util.stream.Stream;
import org.freedesktop.gstreamer.glib.NativeObject;

public final class Video {
  private Video() {}

  public static class Types implements NativeObject.TypeProvider {
    @Override
    public Stream<NativeObject.TypeRegistration<?>> types() {
      return Stream.of(registration(VideoTimeCodeMeta.class, VideoTimeCodeMeta.GTYPE_NAME, VideoTimeCodeMeta::new));
    }
  }
}
