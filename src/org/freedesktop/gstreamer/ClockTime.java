package org.freedesktop.gstreamer;

import java.util.concurrent.TimeUnit;

public final class ClockTime {
  public static final long NONE = -1;
  public static final long ZERO = 0;

  private ClockTime() {}

  public static long fromMicros(long microseconds) {
    return TimeUnit.MICROSECONDS.toNanos(microseconds);
  }

  public static long toMicros(long clocktime) {
    return TimeUnit.NANOSECONDS.toMicros(clocktime);
  }

  public static long fromMillis(long milliseconds) {
    return TimeUnit.MILLISECONDS.toNanos(milliseconds);
  }

  public static long toMillis(long clocktime) {
    return TimeUnit.NANOSECONDS.toMillis(clocktime);
  }

  public static long fromSeconds(long seconds) {
    return TimeUnit.SECONDS.toNanos(seconds);
  }

  public static long toSeconds(long clocktime) {
    return TimeUnit.NANOSECONDS.toSeconds(clocktime);
  }

  public static long getHoursComponent(long clocktime) {
    return (toSeconds(clocktime) / 3600) % 24;
  }

  public static long getMinutesComponent(long clocktime) {
    return (toSeconds(clocktime) / 60) % 60;
  }

  public static long getSecondsComponent(long clocktime) {
    return toSeconds(clocktime) % 60;
  }

  public static boolean isValid(long clocktime) {
    return clocktime != NONE;
  }

  public static String toString(long clocktime) {
    return String.format("%02d:%02d:%02d", getHoursComponent(clocktime), getMinutesComponent(clocktime), getSecondsComponent(clocktime));
  }
}
