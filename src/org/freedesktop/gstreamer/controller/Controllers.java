package org.freedesktop.gstreamer.controller;

import static org.freedesktop.gstreamer.glib.Natives.registration;

import java.util.stream.Stream;
import org.freedesktop.gstreamer.glib.NativeObject;

public class Controllers implements NativeObject.TypeProvider {
  @Override
  public Stream<NativeObject.TypeRegistration<?>> types() {
    return Stream.of(registration(ARGBControlBinding.class, ARGBControlBinding.GTYPE_NAME, ARGBControlBinding::new), registration(DirectControlBinding.class, DirectControlBinding.GTYPE_NAME, DirectControlBinding::new), registration(ProxyControlBinding.class, ProxyControlBinding.GTYPE_NAME, ProxyControlBinding::new), registration(InterpolationControlSource.class, InterpolationControlSource.GTYPE_NAME, InterpolationControlSource::new), registration(TriggerControlSource.class, TriggerControlSource.GTYPE_NAME, TriggerControlSource::new), registration(LFOControlSource.class, LFOControlSource.GTYPE_NAME, LFOControlSource::new), registration(TimedValueControlSource.class, TimedValueControlSource.GTYPE_NAME, TimedValueControlSource::new));
  }
}
