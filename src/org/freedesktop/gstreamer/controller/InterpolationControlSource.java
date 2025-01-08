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

package org.freedesktop.gstreamer.controller;

import static org.freedesktop.gstreamer.lowlevel.GstControllerAPI.GSTCONTROLLER_API;

import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.GstInterpolationControlSourcePtr;

public class InterpolationControlSource extends TimedValueControlSource {
  public static final String GTYPE_NAME = "GstInterpolationControlSource";

  public InterpolationControlSource() {
    this(new Handle(GSTCONTROLLER_API.gst_interpolation_control_source_new(), true), false);
  }

  InterpolationControlSource(Initializer init) {
    this(new Handle(init.ptr.as(GstInterpolationControlSourcePtr.class, GstInterpolationControlSourcePtr::new), init.ownsHandle), init.needRef);
  }

  private InterpolationControlSource(Handle handle, boolean needRef) {
    super(handle, needRef);
  }

  public InterpolationControlSource setMode(InterpolationMode mode) {
    set("mode", mode.intValue());
    return this;
  }

  public InterpolationMode getMode() {
    Object val = get("mode");
    if (val instanceof Integer) {
      int nativeInt = (Integer) val;
      return NativeEnum.fromInt(InterpolationMode.class, nativeInt);
    }
    return InterpolationMode.NONE;
  }

  private static class Handle extends TimedValueControlSource.Handle {
    public Handle(GstInterpolationControlSourcePtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }
  }
}
