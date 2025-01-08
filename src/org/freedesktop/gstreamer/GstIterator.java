package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstIteratorAPI.GSTITERATOR_API;

import java.util.ArrayList;
import java.util.List;
import org.freedesktop.gstreamer.glib.NativeObject;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.freedesktop.gstreamer.lowlevel.GValueAPI;
import org.freedesktop.gstreamer.lowlevel.GstIteratorPtr;
import org.freedesktop.gstreamer.lowlevel.GstTypes;

class GstIterator {
  static <T extends NativeObject> List<T> asList(GstIteratorPtr iter, Class<T> type) {
    final GType gtype = GstTypes.typeFor(type);
    final GValueAPI.GValue gValue = new GValueAPI.GValue(gtype);
    List<T> list = new ArrayList<>();
    while (GSTITERATOR_API.gst_iterator_next(iter, gValue) == 1) {
      list.add((T) gValue.getValue());
    }
    gValue.reset();
    GSTITERATOR_API.gst_iterator_free(iter);
    return list;
  }
}
