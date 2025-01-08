package org.freedesktop.gstreamer.controller;

import static org.freedesktop.gstreamer.lowlevel.GstControllerAPI.GSTCONTROLLER_API;

import java.util.ArrayList;
import java.util.List;
import org.freedesktop.gstreamer.ControlSource;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;
import org.freedesktop.gstreamer.lowlevel.GstTimedValueControlSourcePtr;

public class TimedValueControlSource extends ControlSource {
  public static final String GTYPE_NAME = "GstTimedValueControlSource";
  private final Handle handle;

  protected TimedValueControlSource(Handle handle, boolean needRef) {
    super(handle, needRef);
    this.handle = handle;
  }

  TimedValueControlSource(Initializer init) {
    this(new Handle(init.ptr.as(GstTimedValueControlSourcePtr.class, GstTimedValueControlSourcePtr::new), init.ownsHandle), init.needRef);
  }

  public boolean set(long timestamp, double value) {
    return GSTCONTROLLER_API.gst_timed_value_control_source_set(handle.getPointer(), timestamp, value);
  }

  public boolean setFromList(List<TimedValue> timedValues) {
    for (TimedValue timedvalue : timedValues) {
      boolean ok = set(timedvalue.timestamp, timedvalue.value);
      if (!ok) {
        return false;
      }
    }
    return true;
  }

  public List<TimedValue> getAll() {
    GList next = GSTCONTROLLER_API.gst_timed_value_control_source_get_all(handle.getPointer());
    List<TimedValue> list = new ArrayList<>();
    while (next != null) {
      if (next.data != null) {
        list.add(new TimedValue(next.data.getLong(0), next.data.getDouble(Long.BYTES)));
      }
      next = next.next();
    }
    return list;
  }

  public boolean unset(long timestamp) {
    return GSTCONTROLLER_API.gst_timed_value_control_source_unset(handle.getPointer(), timestamp);
  }

  public void unsetAll() {
    GSTCONTROLLER_API.gst_timed_value_control_source_unset_all(handle.getPointer());
  }

  public int getCount() {
    return GSTCONTROLLER_API.gst_timed_value_control_source_get_count(handle.getPointer());
  }

  public void invalidateCache() {
    GSTCONTROLLER_API.gst_timed_value_control_invalidate_cache(handle.getPointer());
  }

  protected static class Handle extends ControlSource.Handle {
    public Handle(GstTimedValueControlSourcePtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected GstTimedValueControlSourcePtr getPointer() {
      return (GstTimedValueControlSourcePtr) super.getPointer();
    }
  }
}
