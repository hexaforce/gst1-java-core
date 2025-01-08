package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.device.Device;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

public interface GstDeviceAPI extends com.sun.jna.Library {
  GstDeviceAPI GSTDEVICE_API = GstNative.load(GstDeviceAPI.class);

  @CallerOwnsReturn Element gst_device_create_element(Device device, String name);

  @CallerOwnsReturn Caps gst_device_get_caps(Device device);

  Pointer gst_device_get_device_class(Device device);

  Pointer gst_device_get_display_name(Device device);

  boolean gst_device_has_classes(Device device, String classes);

  boolean gst_device_has_classesv(Device device, String[] classes);

  boolean gst_device_reconfigure_element(Device device, Element element);

  Structure gst_device_get_properties(Device device);
}
