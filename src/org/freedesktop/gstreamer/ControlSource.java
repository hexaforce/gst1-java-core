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

import static org.freedesktop.gstreamer.lowlevel.GstControlSourceAPI.GSTCONTROLSOURCE_API;

import org.freedesktop.gstreamer.lowlevel.GstControlSourcePtr;

public class ControlSource extends GstObject {
  public static final String GTYPE_NAME = "GstControlSource";
  private final Handle handle;

  protected ControlSource(Handle handle, boolean needRef) {
    super(handle, needRef);
    this.handle = handle;
  }

  ControlSource(Initializer init) {
    this(new Handle(init.ptr.as(GstControlSourcePtr.class, GstControlSourcePtr::new), init.ownsHandle), init.needRef);
  }

  public double getValue(long timestamp) {
    double[] out = new double[1];
    boolean ok = GSTCONTROLSOURCE_API.gst_control_source_get_value(handle.getPointer(), timestamp, out);
    if (ok) {
      return out[0];
    } else {
      throw new IllegalStateException();
    }
  }

  public boolean getValueArray(long timestamp, long interval, double[] values) {
    return GSTCONTROLSOURCE_API.gst_control_source_get_value_array(handle.getPointer(), timestamp, interval, values.length, values);
  }

  public static final class TimedValue {
    public final long timestamp;
    public final double value;

    public TimedValue(long timestamp, double value) {
      this.timestamp = timestamp;
      this.value = value;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 37 * hash + (int) (this.timestamp ^ (this.timestamp >>> 32));
      hash = 37 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final TimedValue other = (TimedValue) obj;
      if (this.timestamp != other.timestamp) {
        return false;
      }
      if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
        return false;
      }
      return true;
    }

    @Override
    public String toString() {
      return "TimedValue{"
          + "timestamp=" + timestamp + ", value=" + value + '}';
    }
  }

  protected static class Handle extends GstObject.Handle {
    public Handle(GstControlSourcePtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected GstControlSourcePtr getPointer() {
      return (GstControlSourcePtr) super.getPointer();
    }
  }
}
