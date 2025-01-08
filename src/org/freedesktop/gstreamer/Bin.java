package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstBinAPI.GSTBIN_API;

import java.util.EnumSet;
import java.util.List;
import org.freedesktop.gstreamer.glib.NativeFlags;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;
import org.freedesktop.gstreamer.lowlevel.GstIteratorPtr;
import org.freedesktop.gstreamer.lowlevel.GstObjectPtr;

@SuppressWarnings("unused")
public class Bin extends Element {
  public static final String GST_NAME = "bin";
  public static final String GTYPE_NAME = "GstBin";

  protected Bin(Initializer init) {
    super(init);
  }

  Bin(Handle handle, boolean needRef) {
    super(handle, needRef);
  }

  public Bin() {
    this(Natives.initializer(GSTBIN_API.ptr_gst_bin_new(null), false, true));
  }

  public Bin(String name) {
    this(Natives.initializer(GSTBIN_API.ptr_gst_bin_new(name), false, true));
  }

  public boolean add(Element element) {
    return GSTBIN_API.gst_bin_add(this, element);
  }

  public void addMany(Element... elements) {
    GSTBIN_API.gst_bin_add_many(this, elements);
  }

  public boolean remove(Element element) {
    return GSTBIN_API.gst_bin_remove(this, element);
  }

  public void removeMany(Element... elements) {
    GSTBIN_API.gst_bin_remove_many(this, elements);
  }

  private List<Element> elementList(GstIteratorPtr iter) {
    return GstIterator.asList(iter, Element.class);
  }

  public List<Element> getElements() {
    return elementList(GSTBIN_API.gst_bin_iterate_elements(this));
  }

  public List<Element> getElementsSorted() {
    return elementList(GSTBIN_API.gst_bin_iterate_sorted(this));
  }

  public List<Element> getElementsRecursive() {
    return elementList(GSTBIN_API.gst_bin_iterate_recurse(this));
  }

  public List<Element> getSinks() {
    return elementList(GSTBIN_API.gst_bin_iterate_sinks(this));
  }

  public List<Element> getSources() {
    return elementList(GSTBIN_API.gst_bin_iterate_sources(this));
  }

  public Element getElementByName(String name) {
    return GSTBIN_API.gst_bin_get_by_name(this, name);
  }

  public Element getElementByNameRecurseUp(String name) {
    return GSTBIN_API.gst_bin_get_by_name_recurse_up(this, name);
  }

  public void debugToDotFile(EnumSet<DebugGraphDetails> details, String fileName) {
    GSTBIN_API.gst_debug_bin_to_dot_file(this, NativeFlags.toInt(details), fileName);
  }

  public void debugToDotFileWithTS(EnumSet<DebugGraphDetails> details, String fileName) {
    GSTBIN_API.gst_debug_bin_to_dot_file_with_ts(this, NativeFlags.toInt(details), fileName);
  }

  public static interface ELEMENT_ADDED {
    public void elementAdded(Bin bin, Element element);
  }

  public void connect(final ELEMENT_ADDED listener) {
    connect(ELEMENT_ADDED.class, listener, new GstCallback() {
      public void callback(Bin bin, Element elem) {
        listener.elementAdded(bin, elem);
      }
    });
  }

  public void disconnect(ELEMENT_ADDED listener) {
    disconnect(ELEMENT_ADDED.class, listener);
  }

  public static interface ELEMENT_REMOVED {
    public void elementRemoved(Bin bin, Element element);
  }

  public void connect(final ELEMENT_REMOVED listener) {
    connect(ELEMENT_REMOVED.class, listener, new GstCallback() {
      public void callback(Bin bin, Element elem) {
        listener.elementRemoved(bin, elem);
      }
    });
  }

  public void disconnect(ELEMENT_REMOVED listener) {
    disconnect(ELEMENT_REMOVED.class, listener);
  }

  @Gst.Since(minor = 10)
  public static interface DEEP_ELEMENT_ADDED {
    public void elementAdded(Bin bin, Bin sub_bin, Element element);
  }

  @Gst.Since(minor = 10)
  public void connect(final DEEP_ELEMENT_ADDED listener) {
    Gst.checkVersion(1, 10);
    connect(DEEP_ELEMENT_ADDED.class, listener, new GstCallback() {
      public void callback(Bin bin, Bin sub_bin, Element elem) {
        listener.elementAdded(bin, sub_bin, elem);
      }
    });
  }

  @Gst.Since(minor = 10)
  public void disconnect(DEEP_ELEMENT_ADDED listener) {
    disconnect(DEEP_ELEMENT_ADDED.class, listener);
  }

  @Gst.Since(minor = 10)
  public static interface DEEP_ELEMENT_REMOVED {
    public void elementRemoved(Bin bin, Bin sub_bin, Element element);
  }

  @Gst.Since(minor = 10)
  public void connect(final DEEP_ELEMENT_REMOVED listener) {
    Gst.checkVersion(1, 10);
    connect(DEEP_ELEMENT_REMOVED.class, listener, new GstCallback() {
      public void callback(Bin bin, Bin sub_bin, Element elem) {
        listener.elementRemoved(bin, sub_bin, elem);
      }
    });
  }

  @Gst.Since(minor = 10)
  public void disconnect(DEEP_ELEMENT_REMOVED listener) {
    disconnect(DEEP_ELEMENT_REMOVED.class, listener);
  }

  public static interface DO_LATENCY {
    public void doLatency(Bin bin);
  }

  public void connect(final DO_LATENCY listener) {
    connect(DO_LATENCY.class, listener, new GstCallback() {
      public void callback(Bin bin) {
        listener.doLatency(bin);
      }
    });
  }

  public void disconnect(DO_LATENCY listener) {
    disconnect(DO_LATENCY.class, listener);
  }

  public static enum DebugGraphDetails implements NativeFlags<DebugGraphDetails> {
    SHOW_MEDIA_TYPE(1 << 0),
    SHOW_CAPS_DETAILS(1 << 1),
    SHOW_NON_DEFAULT_PARAMS(1 << 2),
    SHOW_STATES(1 << 3);
    public static final EnumSet<DebugGraphDetails> SHOW_ALL = EnumSet.allOf(DebugGraphDetails.class);
    private final int value;

    private DebugGraphDetails(int value) {
      this.value = value;
    }

    @Override
    public int intValue() {
      return value;
    }
  }

  static class Handle extends Element.Handle {
    public Handle(GstObjectPtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }
  }
}
