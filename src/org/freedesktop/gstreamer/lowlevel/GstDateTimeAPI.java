package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

public interface GstDateTimeAPI extends com.sun.jna.Library {
  GstDateTimeAPI GSTDATETIME_API = GstNative.load(GstDateTimeAPI.class);

  GType gst_date_time_get_type();

  int gst_date_time_get_year(Pointer datetime);

  int gst_date_time_get_month(Pointer datetime);

  int gst_date_time_get_day(Pointer datetime);

  int gst_date_time_get_hour(Pointer datetime);

  int gst_date_time_get_minute(Pointer datetime);

  int gst_date_time_get_second(Pointer datetime);

  int gst_date_time_get_microsecond(Pointer datetime);

  float gst_date_time_get_time_zone_offset(Pointer datetime);

  @CallerOwnsReturn Pointer gst_date_time_new_from_unix_epoch_local_time(long secs);

  @CallerOwnsReturn Pointer gst_date_time_new_from_unix_epoch_utc(long secs);

  @CallerOwnsReturn Pointer gst_date_time_new_local_time(int year, int month, int day, int hour, int minute, double seconds);

  @CallerOwnsReturn Pointer gst_date_time_new(float tzoffset, int year, int month, int day, int hour, int minute, double seconds);

  @CallerOwnsReturn Pointer gst_date_time_new_now_local_time();

  @CallerOwnsReturn Pointer gst_date_time_new_now_utc();

  Pointer gst_date_time_ref(Pointer datetime);

  void gst_date_time_unref(Pointer datetime);
}
