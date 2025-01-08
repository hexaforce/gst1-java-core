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

import org.freedesktop.gstreamer.glib.RefCountedObject;
import org.freedesktop.gstreamer.lowlevel.GPointer;

public class ClockID extends RefCountedObject implements Comparable<ClockID> {
  ClockID(Initializer init) {
    super(new Handle(init.ptr, init.ownsHandle), init.needRef);
  }

  public void unschedule() {
    GSTCLOCK_API.gst_clock_id_unschedule(this);
  }

  public long getTime() {
    return GSTCLOCK_API.gst_clock_id_get_time(this);
  }

  @Override
  public int compareTo(ClockID other) {
    return GSTCLOCK_API.gst_clock_id_compare_func(this, other);
  }

  private static final class Handle extends RefCountedObject.Handle {
    public Handle(GPointer ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {
      GSTCLOCK_API.gst_clock_id_unref(ptr);
    }

    @Override
    protected void ref() {
      GSTCLOCK_API.gst_clock_id_ref(getPointer());
    }

    @Override
    protected void unref() {
      GSTCLOCK_API.gst_clock_id_unref(getPointer());
    }
  }
}
