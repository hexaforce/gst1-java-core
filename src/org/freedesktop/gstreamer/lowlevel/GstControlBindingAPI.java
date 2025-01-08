package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Library;

public interface GstControlBindingAPI extends Library {
  GstControlBindingAPI GSTCONTROLBINDING_API = GstNative.load(GstControlBindingAPI.class);

  boolean gst_control_binding_sync_values(GstControlBindingPtr binding, GstObjectPtr object, long timestamp, long lastSync);

  GValueAPI.GValue gst_control_binding_get_value(GstControlBindingPtr binding, long timestamp);

  boolean gst_control_binding_get_g_value_array(GstControlBindingPtr binding, long timestamp, long internal, int n_values, GValueAPI.GValue[] values);

  void gst_control_binding_set_disabled(GstControlBindingPtr binding, boolean disabled);

  boolean gst_control_binding_is_disabled(GstControlBindingPtr binding);
}
