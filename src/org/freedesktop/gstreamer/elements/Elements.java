package org.freedesktop.gstreamer.elements;

import static org.freedesktop.gstreamer.glib.Natives.registration;

import java.util.stream.Stream;
import org.freedesktop.gstreamer.glib.NativeObject;

public class Elements implements NativeObject.TypeProvider {
  @Override
  public Stream<NativeObject.TypeRegistration<?>> types() {
    return Stream.of(registration(AppSink.class, AppSink.GTYPE_NAME, AppSink::new), registration(AppSrc.class, AppSrc.GTYPE_NAME, AppSrc::new), registration(BaseSrc.class, BaseSrc.GTYPE_NAME, BaseSrc::new), registration(BaseSink.class, BaseSink.GTYPE_NAME, BaseSink::new), registration(BaseTransform.class, BaseTransform.GTYPE_NAME, BaseTransform::new), registration(DecodeBin.class, DecodeBin.GTYPE_NAME, DecodeBin::new), registration(PlayBin.class, PlayBin.GTYPE_NAME, PlayBin::new), registration(URIDecodeBin.class, URIDecodeBin.GTYPE_NAME, URIDecodeBin::new));
  }
}
