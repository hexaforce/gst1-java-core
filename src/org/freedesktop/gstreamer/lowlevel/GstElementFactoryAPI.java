package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.PadDirection;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;
import org.freedesktop.gstreamer.lowlevel.annotations.Const;

public interface GstElementFactoryAPI extends com.sun.jna.Library {
  GstElementFactoryAPI GSTELEMENTFACTORY_API = GstNative.load(GstElementFactoryAPI.class);

  GType gst_element_factory_get_type();

  ElementFactory gst_element_factory_find(String factoryName);

  @CallerOwnsReturn Pointer ptr_gst_element_factory_make(String factoryName, String elementName);

  @CallerOwnsReturn Pointer ptr_gst_element_factory_create(ElementFactory factory, String elementName);

  @CallerOwnsReturn Element gst_element_factory_make(String factoryName, String elementName);

  @CallerOwnsReturn Element gst_element_factory_create(ElementFactory factory, String elementName);

  GType gst_element_factory_get_element_type(ElementFactory factory);

  String gst_element_factory_get_metadata(ElementFactory factory, String key);

  int gst_element_factory_get_num_pad_templates(ElementFactory factory);

  int gst_element_factory_get_uri_type(ElementFactory factory);

  GList gst_element_factory_get_static_pad_templates(ElementFactory factory);

  GList gst_element_factory_list_get_elements(long type, int minrank);

  GList gst_element_factory_list_filter(GList list, @Const Caps caps, PadDirection direction, boolean subsetonly);

  boolean gst_element_factory_can_src_caps(ElementFactory factory, @Const Caps caps);

  boolean gst_element_factory_can_sink_caps(ElementFactory factory, @Const Caps caps);
}
