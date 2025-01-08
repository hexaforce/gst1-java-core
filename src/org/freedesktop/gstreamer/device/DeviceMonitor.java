package org.freedesktop.gstreamer.device;

import static org.freedesktop.gstreamer.lowlevel.GstDeviceMonitorAPI.GSTDEVICEMONITOR_API;

import java.util.ArrayList;
import java.util.List;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;

public class DeviceMonitor extends GstObject {
  public static final String GTYPE_NAME = "GstDeviceMonitor";

  public DeviceMonitor() {
    super(Natives.initializer(GSTDEVICEMONITOR_API.ptr_gst_device_monitor_new(), false, true));
  }

  DeviceMonitor(Initializer init) {
    super(init);
  }

  public Bus getBus() {
    return GSTDEVICEMONITOR_API.gst_device_monitor_get_bus(this);
  }

  public int addFilter(String classes, Caps caps) {
    return GSTDEVICEMONITOR_API.gst_device_monitor_add_filter(this, classes, caps);
  }

  public boolean removeFilter(int filterId) {
    return GSTDEVICEMONITOR_API.gst_device_monitor_remove_filter(this, filterId);
  }

  public boolean start() {
    return GSTDEVICEMONITOR_API.gst_device_monitor_start(this);
  }

  public void stop() {
    GSTDEVICEMONITOR_API.gst_device_monitor_stop(this);
  }

  public List<Device> getDevices() {
    GList glist = GSTDEVICEMONITOR_API.gst_device_monitor_get_devices(this);
    List<Device> list = new ArrayList<>();
    GList next = glist;
    while (next != null) {
      if (next.data != null) {
        Device dev = new Device(Natives.initializer(next.data, false, true));
        list.add(dev);
      }
      next = next.next();
    }
    return list;
  }
}
