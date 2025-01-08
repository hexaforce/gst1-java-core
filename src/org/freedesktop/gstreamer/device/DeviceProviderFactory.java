package org.freedesktop.gstreamer.device;

import static org.freedesktop.gstreamer.lowlevel.GstDeviceProviderFactoryAPI.GSTDEVICEPROVIDERFACTORY_API;

import java.util.ArrayList;
import java.util.List;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.PluginFeature.Rank;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;
import org.freedesktop.gstreamer.lowlevel.GstPluginAPI;

public class DeviceProviderFactory extends GstObject {
  public static final String GTYPE_NAME = "GstDeviceProviderFactory";

  DeviceProviderFactory(Initializer init) {
    super(init);
  }

  public DeviceProvider get() {
    return GSTDEVICEPROVIDERFACTORY_API.gst_device_provider_factory_get(this);
  }

  public String getMetadata(String key) {
    return GSTDEVICEPROVIDERFACTORY_API.gst_device_provider_factory_get_metadata(this, key);
  }

  public boolean hasClasses(String classes) {
    return GSTDEVICEPROVIDERFACTORY_API.gst_device_provider_factory_has_classes(this, classes);
  }

  public static DeviceProviderFactory find(String name) {
    return GSTDEVICEPROVIDERFACTORY_API.gst_device_provider_factory_find(name);
  }

  public static DeviceProvider getByName(String factoryName) {
    return GSTDEVICEPROVIDERFACTORY_API.gst_device_provider_factory_get_by_name(factoryName);
  }

  public static List<DeviceProviderFactory> getDeviceProviders(Rank minRank) {
    GList glist = GSTDEVICEPROVIDERFACTORY_API.gst_device_provider_factory_list_get_device_providers(minRank);
    List<DeviceProviderFactory> list = new ArrayList<>();
    GList next = glist;
    while (next != null) {
      if (next.data != null) {
        DeviceProviderFactory factory = new DeviceProviderFactory(Natives.initializer(next.data, true, true));
        list.add(factory);
      }
      next = next.next();
    }
    GstPluginAPI.GSTPLUGIN_API.gst_plugin_list_free(glist);
    return list;
  }
}
