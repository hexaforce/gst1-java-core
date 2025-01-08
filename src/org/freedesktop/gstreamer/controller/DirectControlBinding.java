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

import org.freedesktop.gstreamer.ControlBinding;
import org.freedesktop.gstreamer.ControlSource;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstControlSourcePtr;
import org.freedesktop.gstreamer.lowlevel.GstDirectControlBindingPtr;
import org.freedesktop.gstreamer.lowlevel.GstObjectPtr;

public class DirectControlBinding extends ControlBinding {
  public static final String GTYPE_NAME = "GstDirectControlBinding";

  DirectControlBinding(Initializer init) {
    this(new Handle(init.ptr.as(GstDirectControlBindingPtr.class, GstDirectControlBindingPtr::new), init.ownsHandle), init.needRef);
  }

  private DirectControlBinding(Handle handle, boolean needRef) {
    super(handle, needRef);
  }

  public static DirectControlBinding create(GstObject object, String propertyName, ControlSource controlSource) {
    GstDirectControlBindingPtr ptr = GSTCONTROLLER_API.gst_direct_control_binding_new(Natives.getPointer(object).as(GstObjectPtr.class, GstObjectPtr::new), propertyName, Natives.getPointer(controlSource).as(GstControlSourcePtr.class, GstControlSourcePtr::new));
    return new DirectControlBinding(new Handle(ptr, true), false);
  }

  public static DirectControlBinding createAbsolute(GstObject object, String propertyName, ControlSource controlSource) {
    GstDirectControlBindingPtr ptr = GSTCONTROLLER_API.gst_direct_control_binding_new_absolute(Natives.getPointer(object).as(GstObjectPtr.class, GstObjectPtr::new), propertyName, Natives.getPointer(controlSource).as(GstControlSourcePtr.class, GstControlSourcePtr::new));
    return new DirectControlBinding(new Handle(ptr, true), false);
  }
}
