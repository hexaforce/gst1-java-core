package org.freedesktop.gstreamer.device;

import static org.freedesktop.gstreamer.lowlevel.GlibAPI.GLIB_API;
import static org.freedesktop.gstreamer.lowlevel.GstDeviceAPI.GSTDEVICE_API;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.Structure;

public class Device extends GstObject {
  public static final String GTYPE_NAME = "GstDevice";

  Device(Initializer init) {
    super(init);
  }

  public Element createElement(String name) {
    return GSTDEVICE_API.gst_device_create_element(this, name);
  }

  public Caps getCaps() {
    return GSTDEVICE_API.gst_device_get_caps(this);
  }

  public String getDeviceClass() {
    Pointer ptr = GSTDEVICE_API.gst_device_get_device_class(this);
    String ret = ptr.getString(0);
    GLIB_API.g_free(ptr);
    return ret;
  }

  public String getDisplayName() {
    Pointer ptr = GSTDEVICE_API.gst_device_get_display_name(this);
    String ret = ptr.getString(0);
    GLIB_API.g_free(ptr);
    return ret;
  }

  public boolean hasClasses(String classes) {
    return GSTDEVICE_API.gst_device_has_classes(this, classes);
  }

  public boolean hasClasses(String[] classes) {
    return GSTDEVICE_API.gst_device_has_classesv(this, classes);
  }

  public boolean reconfigureElement(Element element) {
    return GSTDEVICE_API.gst_device_reconfigure_element(this, element);
  }

  public Structure getProperties() {
    return GSTDEVICE_API.gst_device_get_properties(this);
  }
}
