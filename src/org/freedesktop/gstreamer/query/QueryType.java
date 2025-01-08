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

package org.freedesktop.gstreamer.query;

import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.NativeEnum;

public enum QueryType implements NativeEnum<QueryType> {
  UNKNOWN(0, 0),
  POSITION(10, Flags.BOTH),
  DURATION(20, Flags.BOTH),
  LATENCY(30, Flags.BOTH),
  JITTER(40, Flags.BOTH),
  RATE(50, Flags.BOTH),
  SEEKING(60, Flags.BOTH),
  SEGMENT(70, Flags.BOTH),
  CONVERT(80, Flags.BOTH),
  FORMATS(90, Flags.BOTH),
  BUFFERING(110, Flags.BOTH),
  CUSTOM(120, Flags.BOTH),
  URI(130, Flags.BOTH),
  ALLOCATION(140, Flags.DOWNSTREAM | Flags.SERIALIZED),
  SCHEDULING(150, Flags.UPSTREAM),
  ACCEPT_CAPS(160, Flags.BOTH),
  CAPS(170, Flags.BOTH),
  DRAIN(180, Flags.DOWNSTREAM | Flags.SERIALIZED),
  CONTEXT(190, Flags.BOTH),
  @Gst.Since(minor = 16) BITRATE(200, Flags.DOWNSTREAM);
  private static final int SHIFT = 8;
  private final int value;

  private QueryType(int num, int flags) {
    this.value = (num << SHIFT) | flags;
  }

  private QueryType(int value) {
    this.value = value;
  }

  public int intValue() {
    return value;
  }

  private static final class Flags {
    public static final int UPSTREAM = 1 << 0;
    public static final int DOWNSTREAM = 1 << 1;
    public static final int SERIALIZED = 1 << 2;
    public static final int BOTH = UPSTREAM | DOWNSTREAM;
  }
}
