package org.freedesktop.gstreamer.lowlevel;

public interface GstIteratorAPI extends com.sun.jna.Library {
  GstIteratorAPI GSTITERATOR_API = GstNative.load(GstIteratorAPI.class);

  void gst_iterator_free(GstIteratorPtr iter);

  int gst_iterator_next(GstIteratorPtr iter, GValueAPI.GValue next);

  void gst_iterator_resync(GstIteratorPtr iter);
}
