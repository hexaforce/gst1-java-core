package org.freedesktop.gstreamer.controller;

import static org.freedesktop.gstreamer.lowlevel.GstControllerAPI.GSTCONTROLLER_API;

import org.freedesktop.gstreamer.ControlBinding;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstObjectPtr;
import org.freedesktop.gstreamer.lowlevel.GstProxyControlBindingPtr;

@Gst.Since(minor = 12)
public class ProxyControlBinding extends ControlBinding {
  public static final String GTYPE_NAME = "GstProxyControlBinding";

  ProxyControlBinding(Initializer init) {
    this(new Handle(init.ptr.as(GstProxyControlBindingPtr.class, GstProxyControlBindingPtr::new), init.ownsHandle), init.needRef);
  }

  private ProxyControlBinding(Handle handle, boolean needRef) {
    super(handle, needRef);
  }

  @Gst.Since(minor = 12)
  public static ProxyControlBinding create(GstObject object, String propertyName, GstObject refObject, String refPropertyName) {
    GstProxyControlBindingPtr ptr = GSTCONTROLLER_API.gst_proxy_control_binding_new(Natives.getPointer(object).as(GstObjectPtr.class, GstObjectPtr::new), propertyName, Natives.getPointer(refObject).as(GstObjectPtr.class, GstObjectPtr::new), refPropertyName);
    return new ProxyControlBinding(new Handle(ptr, true), false);
  }
}
