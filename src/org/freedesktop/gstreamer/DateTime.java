package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstDateTimeAPI.GSTDATETIME_API;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.glib.NativeObject;
import org.freedesktop.gstreamer.lowlevel.GPointer;

public class DateTime extends NativeObject {
  public static final String GTYPE_NAME = "GstDateTime";

  public static DateTime createInstanceLocalEpoch(long secs) {
    return new DateTime(GSTDATETIME_API.gst_date_time_new_from_unix_epoch_local_time(secs), false, true);
  }

  DateTime(Initializer init) {
    this(new Handle(init.ptr, init.ownsHandle));
  }

  DateTime(Pointer ptr, boolean needRef, boolean ownsHandle) {
    this(new Handle(new GPointer(ptr), ownsHandle));
  }

  DateTime(Handle handle) {
    super(handle);
  }

  public int getYear() {
    return GSTDATETIME_API.gst_date_time_get_year(getRawPointer());
  }

  public int getMonth() {
    return GSTDATETIME_API.gst_date_time_get_month(getRawPointer());
  }

  public int getDay() {
    return GSTDATETIME_API.gst_date_time_get_day(getRawPointer());
  }

  public int getHour() {
    return GSTDATETIME_API.gst_date_time_get_hour(getRawPointer());
  }

  public int getMinute() {
    return GSTDATETIME_API.gst_date_time_get_minute(getRawPointer());
  }

  public int getSecond() {
    return GSTDATETIME_API.gst_date_time_get_second(getRawPointer());
  }

  public int getMicrosecond() {
    return GSTDATETIME_API.gst_date_time_get_microsecond(getRawPointer());
  }

  @Override
  public String toString() {
    return "" + getYear() + "-" + getMonth() + "-" + getDay() + " " + getHour() + ":" + getMinute() + ":" + getSecond() + "." + getMicrosecond();
  }

  private static final class Handle extends NativeObject.Handle {
    public Handle(GPointer ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {
      GSTDATETIME_API.gst_date_time_unref(ptr.getPointer());
    }
  }
}
