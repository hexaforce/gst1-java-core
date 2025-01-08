package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.Bin;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

public interface GstBinAPI extends com.sun.jna.Library {
  GstBinAPI GSTBIN_API = GstNative.load(GstBinAPI.class);

  @CallerOwnsReturn Pointer ptr_gst_bin_new(String name);

  @CallerOwnsReturn Pointer ptr_gst_pipeline_new(String name);

  @CallerOwnsReturn Bin gst_bin_new(String name);

  GType gst_bin_get_type();

  boolean gst_bin_add(Bin bin, Element element);

  void gst_bin_add_many(Bin bin, Element... elements);

  boolean gst_bin_remove(Bin bin, Element element);

  void gst_bin_remove_many(Bin bin, Element... elements);

  @CallerOwnsReturn Element gst_bin_get_by_name(Bin bin, String name);

  @CallerOwnsReturn Element gst_bin_get_by_name_recurse_up(Bin bin, String name);

  @CallerOwnsReturn Element gst_bin_get_by_interface(Bin bin, GType iface);

  GstIteratorPtr gst_bin_iterate_elements(Bin bin);

  GstIteratorPtr gst_bin_iterate_sorted(Bin bin);

  GstIteratorPtr gst_bin_iterate_recurse(Bin bin);

  GstIteratorPtr gst_bin_iterate_sinks(Bin bin);

  GstIteratorPtr gst_bin_iterate_sources(Bin bin);

  GstIteratorPtr gst_bin_iterate_all_by_interface(Bin bin, GType iface);

  void gst_debug_bin_to_dot_file(Bin bin, int details, String file_name);

  void gst_debug_bin_to_dot_file_with_ts(Bin bin, int details, String file_name);
}
