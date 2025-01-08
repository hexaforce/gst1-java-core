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

package org.freedesktop.gstreamer.device;

import static org.freedesktop.gstreamer.lowlevel.GstDeviceProviderAPI.GSTDEVICEPROVIDER_API;

import java.util.ArrayList;
import java.util.List;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GlibAPI;

public class DeviceProvider extends GstObject {
  public static final String GTYPE_NAME = "GstDeviceProvider";

  DeviceProvider(Initializer init) {
    super(init);
  }

  public boolean canMonitor() {
    return GSTDEVICEPROVIDER_API.gst_device_provider_can_monitor(this);
  }

  public Bus getBus() {
    return GSTDEVICEPROVIDER_API.gst_device_provider_get_bus(this);
  }

  public List<Device> getDevices() {
    GlibAPI.GList glist = GSTDEVICEPROVIDER_API.gst_device_provider_get_devices(this);
    List<Device> list = new ArrayList<Device>();
    GlibAPI.GList next = glist;
    while (next != null) {
      if (next.data != null) {
        Device dev = new Device(Natives.initializer(next.data, true, true));
        list.add(dev);
      }
      next = next.next();
    }
    return list;
  }

  public DeviceProviderFactory getFactory() {
    return GSTDEVICEPROVIDER_API.gst_device_provider_get_factory(this);
  }

  public boolean start() {
    return GSTDEVICEPROVIDER_API.gst_device_provider_start(this);
  }

  public void stop() {
    GSTDEVICEPROVIDER_API.gst_device_provider_stop(this);
  }
}
