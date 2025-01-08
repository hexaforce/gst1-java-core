package org.freedesktop.gstreamer.lowlevel;

import org.freedesktop.gstreamer.Structure;

public interface GstContextAPI extends com.sun.jna.Library {
  GstContextAPI GSTCONTEXT_API = GstNative.load(GstContextAPI.class);

  GstContextPtr gst_context_new(String context_type, boolean persistent);

  String gst_context_get_context_type(GstContextPtr context);

  boolean gst_context_has_context_type(GstContextPtr context, String context_type);

  Structure gst_context_get_structure(GstContextPtr context);

  Structure gst_context_writable_structure(GstContextPtr context);

  boolean gst_context_is_persistent(GstContextPtr context);
}
