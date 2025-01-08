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

import static org.freedesktop.gstreamer.lowlevel.GstClockAPI.GSTCLOCK_API;

public class Clock extends GstObject {
  public static final String GTYPE_NAME = "GstClock";

  @Deprecated
  public Clock(Initializer init) {
    super(init);
  }

  public long setResolution(long resolution) {
    return GSTCLOCK_API.gst_clock_set_resolution(this, resolution);
  }

  public long getResolution() {
    return GSTCLOCK_API.gst_clock_get_resolution(this);
  }

  public long getTime() {
    return GSTCLOCK_API.gst_clock_get_time(this);
  }

  public long getInternalTime() {
    return GSTCLOCK_API.gst_clock_get_internal_time(this);
  }

  public Clock getMaster() {
    return GSTCLOCK_API.gst_clock_get_master(this);
  }

  public boolean setMaster(Clock master) {
    return GSTCLOCK_API.gst_clock_set_master(this, master);
  }

  @Deprecated
  public void getCalibration(long internal, long external, long rateNumerator, long rateDenominator) {
    GSTCLOCK_API.gst_clock_set_calibration(this, internal, external, rateNumerator, rateDenominator);
  }

  public Calibration getCalibration() {
    long[] internalPtr = new long[1];
    long[] externalPtr = new long[1];
    long[] rateNumPtr = new long[1];
    long[] rateDenomPtr = new long[1];
    GSTCLOCK_API.gst_clock_get_calibration(this, internalPtr, externalPtr, rateNumPtr, rateDenomPtr);
    return new Calibration(internalPtr[0], externalPtr[0], rateNumPtr[0], rateDenomPtr[0]);
  }

  public void setCalibration(long internal, long external, long rateNumerator, long rateDenominator) {
    GSTCLOCK_API.gst_clock_set_calibration(this, internal, external, rateNumerator, rateDenominator);
  }

  public ClockID newSingleShotID(long time) {
    return GSTCLOCK_API.gst_clock_new_single_shot_id(this, time);
  }

  public ClockID newPeriodicID(long startTime, long interval) {
    return GSTCLOCK_API.gst_clock_new_periodic_id(this, startTime, interval);
  }

  public static final class Calibration {
    private final long internal;
    private final long external;
    private final long rateNum;
    private final long rateDenom;

    private Calibration(long internal, long external, long rateNum, long rateDenom) {
      this.internal = internal;
      this.external = external;
      this.rateNum = rateNum;
      this.rateDenom = rateDenom;
    }

    public long internal() {
      return internal;
    }

    public long external() {
      return external;
    }

    public long rateNum() {
      return rateNum;
    }

    public long rateDenom() {
      return rateDenom;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 59 * hash + (int) (this.internal ^ (this.internal >>> 32));
      hash = 59 * hash + (int) (this.external ^ (this.external >>> 32));
      hash = 59 * hash + (int) (this.rateNum ^ (this.rateNum >>> 32));
      hash = 59 * hash + (int) (this.rateDenom ^ (this.rateDenom >>> 32));
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
      final Calibration other = (Calibration) obj;
      if (this.internal != other.internal) {
        return false;
      }
      if (this.external != other.external) {
        return false;
      }
      if (this.rateNum != other.rateNum) {
        return false;
      }
      if (this.rateDenom != other.rateDenom) {
        return false;
      }
      return true;
    }

    @Override
    public String toString() {
      return "Calibration{"
          + "internal=" + internal + ", external=" + external + ", rateNum=" + rateNum + ", rateDenom=" + rateDenom + '}';
    }
  }
}
