package org.freedesktop.gstreamer.lowlevel;

import org.freedesktop.gstreamer.PluginFeature.Rank;
import org.freedesktop.gstreamer.device.DeviceProvider;
import org.freedesktop.gstreamer.device.DeviceProviderFactory;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;

public interface GstDeviceProviderFactoryAPI extends com.sun.jna.Library {
  GstDeviceProviderFactoryAPI GSTDEVICEPROVIDERFACTORY_API = GstNative.load(GstDeviceProviderFactoryAPI.class);

  DeviceProviderFactory gst_device_provider_factory_find(String name);

  DeviceProvider gst_device_provider_factory_get(DeviceProviderFactory factory);

  DeviceProvider gst_device_provider_factory_get_by_name(String factoryName);

  GType gst_device_provider_factory_get_device_provider_type(DeviceProviderFactory factory);

  String gst_device_provider_factory_get_metadata(DeviceProviderFactory factory, String key);

  String[] gst_device_provider_factory_get_metadata_keys(DeviceProviderFactory factory);

  boolean gst_device_provider_factory_has_classes(DeviceProviderFactory factory, String classes);

  boolean gst_device_provider_factory_has_classesv(DeviceProviderFactory factory, String[] classes);

  GList gst_device_provider_factory_list_get_device_providers(Rank minRank);
}
