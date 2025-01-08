package org.freedesktop.gstreamer.lowlevel;

import org.freedesktop.gstreamer.Plugin;
import org.freedesktop.gstreamer.PluginFeature;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

public interface GstPluginFeatureAPI extends com.sun.jna.Library {
  GstPluginFeatureAPI GSTPLUGINFEATURE_API = GstNative.load(GstPluginFeatureAPI.class);

  GType gst_plugin_feature_get_type();

  @CallerOwnsReturn PluginFeature gst_plugin_feature_load(PluginFeature feature);

  void gst_plugin_feature_set_rank(PluginFeature feature, int rank);

  int gst_plugin_feature_get_rank(PluginFeature feature);

  String gst_plugin_feature_get_plugin_name(PluginFeature feature);

  @CallerOwnsReturn Plugin gst_plugin_feature_get_plugin(PluginFeature feature);

  boolean gst_plugin_feature_check_version(PluginFeature feature, int min_major, int min_minor, int min_micro);

  void gst_plugin_feature_list_free(GList list);
}
