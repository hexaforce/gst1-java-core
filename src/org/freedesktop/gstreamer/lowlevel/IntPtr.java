package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Native;

@SuppressWarnings("serial")
public class IntPtr extends Number {
  public final Number value;

  public IntPtr(int value) {
    this.value = Native.POINTER_SIZE == 8 ? new Long(value) : new Integer(value);
  }

  public String toString() {
    return Integer.toHexString(intValue());
  }

  public int intValue() {
    return value.intValue();
  }

  public long longValue() {
    return value.longValue();
  }

  public float floatValue() {
    return value.floatValue();
  }

  public double doubleValue() {
    return value.doubleValue();
  }
}
