package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.device.DeviceMonitor;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

public interface GstDeviceMonitorAPI extends com.sun.jna.Library {
  GstDeviceMonitorAPI GSTDEVICEMONITOR_API = GstNative.load(GstDeviceMonitorAPI.class);

  @CallerOwnsReturn DeviceMonitor gst_device_monitor_new();

  @CallerOwnsReturn Pointer ptr_gst_device_monitor_new();

  @CallerOwnsReturn Bus gst_device_monitor_get_bus(DeviceMonitor monitor);

  int gst_device_monitor_add_filter(DeviceMonitor monitor, String classes, Caps caps);

  boolean gst_device_monitor_remove_filter(DeviceMonitor monitor, int filterId);

  boolean gst_device_monitor_start(DeviceMonitor monitor);

  void gst_device_monitor_stop(DeviceMonitor monitor);

  GList gst_device_monitor_get_devices(DeviceMonitor monitor);
}
