package org.freedesktop.gstreamer.glib;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.lowlevel.GPointer;
import org.freedesktop.gstreamer.lowlevel.GlibAPI;

public class GDate extends NativeObject {
  public static final String GTYPE_NAME = "GDate";

  GDate(Initializer init) {
    this(new Handle(init.ptr, init.ownsHandle));
  }

  GDate(Handle handle) {
    super(handle);
  }

  public int getDay() {
    return GlibAPI.GLIB_API.g_date_get_day(getRawPointer());
  }

  public int getMonth() {
    return GlibAPI.GLIB_API.g_date_get_month(getRawPointer());
  }

  public int getYear() {
    return GlibAPI.GLIB_API.g_date_get_year(getRawPointer());
  }

  @Override
  public String toString() {
    return "" + getYear() + "-" + getMonth() + "-" + getDay();
  }

  public static GDate createInstance(int day, int month, int year) {
    Pointer ptr = GlibAPI.GLIB_API.g_date_new_dmy(day, month, year);
    return new GDate(new Handle(new GPointer(ptr), true));
  }

  public static GDate createInstance(int julian_day) {
    Pointer ptr = GlibAPI.GLIB_API.g_date_new_julian(julian_day);
    return new GDate(new Handle(new GPointer(ptr), true));
  }

  private static final class Handle extends NativeObject.Handle {
    public Handle(GPointer ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {
      GlibAPI.GLIB_API.g_date_free(ptr.getPointer());
    }
  }
}
