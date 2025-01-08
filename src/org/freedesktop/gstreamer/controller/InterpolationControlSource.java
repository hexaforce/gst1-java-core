package org.freedesktop.gstreamer.controller;

import static org.freedesktop.gstreamer.lowlevel.GstControllerAPI.GSTCONTROLLER_API;

import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.GstInterpolationControlSourcePtr;

public class InterpolationControlSource extends TimedValueControlSource {
  public static final String GTYPE_NAME = "GstInterpolationControlSource";

  public InterpolationControlSource() {
    this(new Handle(GSTCONTROLLER_API.gst_interpolation_control_source_new(), true), false);
  }

  InterpolationControlSource(Initializer init) {
    this(new Handle(init.ptr.as(GstInterpolationControlSourcePtr.class, GstInterpolationControlSourcePtr::new), init.ownsHandle), init.needRef);
  }

  private InterpolationControlSource(Handle handle, boolean needRef) {
    super(handle, needRef);
  }

  public InterpolationControlSource setMode(InterpolationMode mode) {
    set("mode", mode.intValue());
    return this;
  }

  public InterpolationMode getMode() {
    Object val = get("mode");
    if (val instanceof Integer) {
      int nativeInt = (Integer) val;
      return NativeEnum.fromInt(InterpolationMode.class, nativeInt);
    }
    return InterpolationMode.NONE;
  }

  private static class Handle extends TimedValueControlSource.Handle {
    public Handle(GstInterpolationControlSourcePtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }
  }
}
