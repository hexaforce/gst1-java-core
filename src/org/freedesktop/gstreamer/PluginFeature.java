package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstObjectAPI.GSTOBJECT_API;
import static org.freedesktop.gstreamer.lowlevel.GstPluginFeatureAPI.GSTPLUGINFEATURE_API;

import org.freedesktop.gstreamer.glib.NativeEnum;

public class PluginFeature extends GstObject {
  public static final String GTYPE_NAME = "GstPluginFeature";

  public enum Rank implements NativeEnum<Rank> {
    NONE(0),
    MARGINAL(64),
    SECONDARY(128),
    PRIMARY(256);
    private final int value;

    private Rank(int value) {
      this.value = value;
    }

    @Override
    public int intValue() {
      return value;
    }
  }

  PluginFeature(Initializer init) {
    super(init);
  }

  @Override
  public String toString() {
    return getName();
  }

  @Override
  public String getName() {
    return GSTOBJECT_API.gst_object_get_name(this);
  }

  @Override
  public boolean setName(String name) {
    GSTOBJECT_API.gst_object_set_name(this, name);
    return true;
  }

  public void setRank(int rank) {
    GSTPLUGINFEATURE_API.gst_plugin_feature_set_rank(this, rank);
  }

  public void setRank(Rank rank) {
    setRank(rank.intValue());
  }

  public int getRank() {
    return GSTPLUGINFEATURE_API.gst_plugin_feature_get_rank(this);
  }

  public boolean checkVersion(int major, int minor, int micro) {
    return GSTPLUGINFEATURE_API.gst_plugin_feature_check_version(this, minor, minor, micro);
  }

  public String getPluginName() {
    return GSTPLUGINFEATURE_API.gst_plugin_feature_get_plugin_name(this);
  }

  public Plugin getPlugin() {
    return GSTPLUGINFEATURE_API.gst_plugin_feature_get_plugin(this);
  }
}
