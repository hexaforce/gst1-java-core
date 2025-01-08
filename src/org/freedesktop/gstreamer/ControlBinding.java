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

import static org.freedesktop.gstreamer.lowlevel.GstControlBindingAPI.GSTCONTROLBINDING_API;

import org.freedesktop.gstreamer.lowlevel.GValueAPI;
import org.freedesktop.gstreamer.lowlevel.GstControlBindingPtr;

public class ControlBinding extends GstObject {
  public static final String GTYPE_NAME = "GstControlBinding";
  private final Handle handle;

  protected ControlBinding(Handle handle, boolean needRef) {
    super(handle, needRef);
    this.handle = handle;
  }

  ControlBinding(Initializer init) {
    this(new Handle(init.ptr.as(GstControlBindingPtr.class, GstControlBindingPtr::new), init.ownsHandle), init.needRef);
  }

  public Object getValue(long timestamp) {
    GValueAPI.GValue gValue = GSTCONTROLBINDING_API.gst_control_binding_get_value(handle.getPointer(), timestamp);
    return gValue == null ? null : gValue.getValue();
  }

  public boolean getValueArray(long timestamp, long interval, Object[] values) {
    GValueAPI.GValue[] gValues = new GValueAPI.GValue[values.length];
    boolean ok = GSTCONTROLBINDING_API.gst_control_binding_get_g_value_array(handle.getPointer(), timestamp, interval, gValues.length, gValues);
    if (ok) {
      for (int i = 0; i < values.length; i++) {
        values[i] = gValues[i].getValue();
      }
    }
    return ok;
  }

  public void setDisabled(boolean disabled) {
    GSTCONTROLBINDING_API.gst_control_binding_set_disabled(handle.getPointer(), disabled);
  }

  public boolean isDisabled() {
    return GSTCONTROLBINDING_API.gst_control_binding_is_disabled(handle.getPointer());
  }

  protected static class Handle extends GstObject.Handle {
    public Handle(GstControlBindingPtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected GstControlBindingPtr getPointer() {
      return (GstControlBindingPtr) super.getPointer();
    }
  }
}
