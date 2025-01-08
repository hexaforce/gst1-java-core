package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Library;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

public interface GstControllerAPI extends Library {
  GstControllerAPI GSTCONTROLLER_API = GstNative.load("gstcontroller", GstControllerAPI.class);

  @CallerOwnsReturn GstTriggerControlSourcePtr gst_trigger_control_source_new();

  @CallerOwnsReturn GstInterpolationControlSourcePtr gst_interpolation_control_source_new();

  @CallerOwnsReturn GstLFOControlSourcePtr gst_lfo_control_source_new();

  @CallerOwnsReturn GstDirectControlBindingPtr gst_direct_control_binding_new(GstObjectPtr object, String property_name, GstControlSourcePtr cs);

  @CallerOwnsReturn GstDirectControlBindingPtr gst_direct_control_binding_new_absolute(GstObjectPtr object, String property_name, GstControlSourcePtr cs);

  @CallerOwnsReturn GstARGBControlBindingPtr gst_argb_control_binding_new(GstObjectPtr object, String property_name, GstControlSourcePtr cs_a, GstControlSourcePtr cs_r, GstControlSourcePtr cs_g, GstControlSourcePtr cs_b);

  @CallerOwnsReturn GstProxyControlBindingPtr gst_proxy_control_binding_new(GstObjectPtr object, String property_name, GstObjectPtr ref_object, String ref_property_name);

  boolean gst_timed_value_control_source_set(GstTimedValueControlSourcePtr self, long timestamp, double value);

  boolean gst_timed_value_control_source_set_from_list(GstTimedValueControlSourcePtr self, GlibAPI.GSList timedvalues);

  @CallerOwnsReturn GlibAPI.GList gst_timed_value_control_source_get_all(GstTimedValueControlSourcePtr self);

  boolean gst_timed_value_control_source_unset(GstTimedValueControlSourcePtr self, long timestamp);

  void gst_timed_value_control_source_unset_all(GstTimedValueControlSourcePtr self);

  int gst_timed_value_control_source_get_count(GstTimedValueControlSourcePtr self);

  void gst_timed_value_control_invalidate_cache(GstTimedValueControlSourcePtr self);
}
