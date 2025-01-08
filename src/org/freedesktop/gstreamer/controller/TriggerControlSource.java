package org.freedesktop.gstreamer.controller;

import static org.freedesktop.gstreamer.lowlevel.GstControllerAPI.GSTCONTROLLER_API;

import org.freedesktop.gstreamer.lowlevel.GstTriggerControlSourcePtr;

public class TriggerControlSource extends TimedValueControlSource {
  public static final String GTYPE_NAME = "GstTriggerControlSource";

  public TriggerControlSource() {
    this(new Handle(GSTCONTROLLER_API.gst_trigger_control_source_new(), true), false);
  }

  TriggerControlSource(Initializer init) {
    this(new Handle(init.ptr.as(GstTriggerControlSourcePtr.class, GstTriggerControlSourcePtr::new), init.ownsHandle), init.needRef);
  }

  private TriggerControlSource(Handle handle, boolean needRef) {
    super(handle, needRef);
  }

  public TriggerControlSource setTolerance(long tolerance) {
    set("tolerance", tolerance);
    return this;
  }

  public long getTolerance() {
    Object val = get("tolerance");
    if (val instanceof Long) {
      return (long) val;
    }
    return 0L;
  }

  private static class Handle extends TimedValueControlSource.Handle {
    public Handle(GstTriggerControlSourcePtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }
  }
}
