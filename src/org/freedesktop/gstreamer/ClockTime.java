/*
 * This file is part of gstreamer-java.
 * 
 * This code is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License version 3 only, as published by the Free Software Foundation.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License version 3 for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3 along with this work.
 * If not, see <http://www.gnu.org/licenses/>.
 */

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
