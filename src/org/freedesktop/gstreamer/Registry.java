package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstPluginAPI.GSTPLUGIN_API;
import static org.freedesktop.gstreamer.lowlevel.GstPluginFeatureAPI.GSTPLUGINFEATURE_API;
import static org.freedesktop.gstreamer.lowlevel.GstRegistryAPI.GSTREGISTRY_API;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;

public class Registry extends GstObject {
  public static final String GTYPE_NAME = "GstRegistry";

  Registry(Initializer init) {
    super(init);
  }

  public Plugin findPlugin(String name) {
    return GSTREGISTRY_API.gst_registry_find_plugin(this, name);
  }

  public boolean addPlugin(Plugin plugin) {
    return GSTREGISTRY_API.gst_registry_add_plugin(this, plugin);
  }

  public void removePlugin(Plugin plugin) {
    GSTREGISTRY_API.gst_registry_remove_plugin(this, plugin);
  }

  public PluginFeature lookupFeature(String name) {
    return GSTREGISTRY_API.gst_registry_lookup_feature(this, name);
  }

  public List<Plugin> getPluginList() {
    GList glist = GSTREGISTRY_API.gst_registry_get_plugin_list(this);
    List<Plugin> list = objectList(glist, Plugin.class);
    GSTPLUGIN_API.gst_plugin_list_free(glist);
    return list;
  }

  public List<Plugin> getPluginList(final PluginFilter filter) {
    return getPluginList().stream().filter(filter::accept).collect(Collectors.toList());
  }

  public List<PluginFeature> getPluginFeatureListByPlugin(String name) {
    GList glist = GSTREGISTRY_API.gst_registry_get_feature_list_by_plugin(this, name);
    List<PluginFeature> list = objectList(glist, PluginFeature.class);
    GSTPLUGINFEATURE_API.gst_plugin_feature_list_free(glist);
    return list;
  }

  public boolean scanPath(String path) {
    return GSTREGISTRY_API.gst_registry_scan_path(this, path);
  }

  private <T extends GstObject> List<T> objectList(GList glist, Class<T> objectClass) {
    List<T> list = new ArrayList<T>();
    GList next = glist;
    while (next != null) {
      if (next.data != null) {
        list.add(Natives.objectFor(next.data, objectClass, true, true));
      }
      next = next.next();
    }
    return list;
  }

  public static interface PluginFilter {
    public boolean accept(Plugin plugin);
  }

  public static Registry get() {
    return Natives.objectFor(GSTREGISTRY_API.gst_registry_get(), Registry.class, false, false);
  }
}
