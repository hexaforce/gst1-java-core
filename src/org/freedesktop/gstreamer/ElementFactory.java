package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstElementFactoryAPI.GSTELEMENTFACTORY_API;
import static org.freedesktop.gstreamer.lowlevel.GstPadTemplateAPI.GSTPADTEMPLATE_API;
import static org.freedesktop.gstreamer.lowlevel.GstPluginAPI.GSTPLUGIN_API;

import com.sun.jna.Pointer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;
import org.freedesktop.gstreamer.lowlevel.GstPadTemplateAPI.GstStaticPadTemplate;

public class ElementFactory extends PluginFeature {
  public static final String GTYPE_NAME = "GstElementFactory";
  private static final Level DEBUG = Level.FINE;
  private static final Logger LOG = Logger.getLogger(ElementFactory.class.getName());

  ElementFactory(Initializer init) {
    super(init);
    if (LOG.isLoggable(Level.FINER)) {
      LOG.entering("ElementFactory", "<init>", new Object[] {init});
    }
  }

  public Element create(String name) {
    if (LOG.isLoggable(Level.FINER)) {
      LOG.entering("ElementFactory", "create", name);
    }
    Pointer elem = GSTELEMENTFACTORY_API.ptr_gst_element_factory_create(this, name);
    LOG.log(DEBUG, "gst_element_factory_create returned: " + elem);
    if (elem == null) {
      throw new IllegalArgumentException("Cannot create GstElement");
    }
    return elementFor(elem, getName());
  }

  public String getAuthor() {
    if (LOG.isLoggable(Level.FINER)) {
      LOG.entering("ElementFactory", "getAuthor");
    }
    return GSTELEMENTFACTORY_API.gst_element_factory_get_metadata(this, "author");
  }

  public String getDescription() {
    if (LOG.isLoggable(Level.FINER)) {
      LOG.entering("ElementFactory", "getDescription");
    }
    return GSTELEMENTFACTORY_API.gst_element_factory_get_metadata(this, "description");
  }

  public String getKlass() {
    if (LOG.isLoggable(Level.FINER)) {
      LOG.entering("ElementFactory", "getKlass");
    }
    return GSTELEMENTFACTORY_API.gst_element_factory_get_metadata(this, "klass");
  }

  public String getLongName() {
    if (LOG.isLoggable(Level.FINER)) {
      LOG.entering("ElementFactory", "getLongName");
    }
    return GSTELEMENTFACTORY_API.gst_element_factory_get_metadata(this, "long-name");
  }

  public List<StaticPadTemplate> getStaticPadTemplates() {
    if (LOG.isLoggable(Level.FINER)) {
      LOG.entering("ElementFactory", "getStaticPadTemplates");
    }
    GList glist = GSTELEMENTFACTORY_API.gst_element_factory_get_static_pad_templates(this);
    LOG.log(DEBUG, "GSTELEMENTFACTORY_API.gst_element_factory_get_static_pad_templates returned: " + glist);
    List<StaticPadTemplate> templates = new ArrayList<StaticPadTemplate>();
    GList next = glist;
    while (next != null) {
      if (next.data != null) {
        GstStaticPadTemplate temp = new GstStaticPadTemplate(next.data);
        templates.add(new StaticPadTemplate(temp.getName(), temp.getPadDirection(), temp.getPadPresence(), GSTPADTEMPLATE_API.gst_static_pad_template_get_caps(temp)));
      }
      next = next.next();
    }
    return templates;
  }

  public static ElementFactory find(String name) {
    if (LOG.isLoggable(Level.FINER)) {
      LOG.entering("ElementFactory", "find", name);
    }
    ElementFactory factory = GSTELEMENTFACTORY_API.gst_element_factory_find(name);
    if (factory == null) {
      throw new IllegalArgumentException("No such Gstreamer factory: " + name);
    }
    return factory;
  }

  public static List<ElementFactory> listGetElements(ListType type, Rank minrank) {
    GList glist = GSTELEMENTFACTORY_API.gst_element_factory_list_get_elements(type.getValue(), minrank.intValue());
    List<ElementFactory> list = new ArrayList<ElementFactory>();
    GList next = glist;
    while (next != null) {
      if (next.data != null) {
        ElementFactory fact = new ElementFactory(Natives.initializer(next.data, true, true));
        list.add(fact);
      }
      next = next.next();
    }
    GSTPLUGIN_API.gst_plugin_list_free(glist);
    return list;
  }

  public static List<ElementFactory> listGetElementsFilter(ListType type, Rank minrank, Caps caps, PadDirection direction, boolean subsetonly) {
    List<ElementFactory> filterList = new ArrayList<ElementFactory>();
    GList glist = GSTELEMENTFACTORY_API.gst_element_factory_list_get_elements(type.getValue(), minrank.intValue());
    GList gFilterList = GSTELEMENTFACTORY_API.gst_element_factory_list_filter(glist, caps, direction, subsetonly);
    GList next = gFilterList;
    while (next != null) {
      if (next.data != null) {
        ElementFactory fact = new ElementFactory(Natives.initializer(next.data, true, true));
        filterList.add(fact);
      }
      next = next.next();
    }
    GSTPLUGIN_API.gst_plugin_list_free(glist);
    GSTPLUGIN_API.gst_plugin_list_free(gFilterList);
    return filterList;
  }

  public static Element make(String factoryName, String name) {
    if (LOG.isLoggable(Level.FINER)) {
      LOG.entering("ElementFactory", "make", new Object[] {factoryName, name});
    }
    return elementFor(makeRawElement(factoryName, name), factoryName);
  }

  static Pointer makeRawElement(String factoryName, String name) {
    if (LOG.isLoggable(Level.FINER)) {
      LOG.entering("ElementFactory", "makeRawElement", new Object[] {factoryName, name});
    }
    Pointer elem = GSTELEMENTFACTORY_API.ptr_gst_element_factory_make(factoryName, name);
    LOG.log(DEBUG, "Return from gst_element_factory_make=" + elem);
    if (elem == null) {
      throw new IllegalArgumentException("No such Gstreamer factory: " + factoryName);
    }
    return elem;
  }

  private static Element elementFor(Pointer ptr, String factoryName) {
    return Natives.objectFor(ptr, Element.class, false, true);
  }

  public enum ListType {
    DECODER((long) 1 << 0),
    ENCODER((long) 1 << 1),
    SINK((long) 1 << 2),
    SRC((long) 1 << 3),
    MUXER((long) 1 << 4),
    DEMUXER((long) 1 << 5),
    PARSER((long) 1 << 6),
    PAYLOADER((long) 1 << 7),
    DEPAYLOADER((long) 1 << 8),
    FORMATTER((long) 1 << 9),
    DECRYPTOR((long) 1 << 10),
    ENCRYPTOR((long) 1 << 11),
    ANY((((long) 1) << 49) - 1),
    MEDIA_ANY(~((long) 0) << 48),
    MEDIA_VIDEO((long) 1 << 49),
    MEDIA_AUDIO((long) 1 << 50),
    MEDIA_IMAGE((long) 1 << 51),
    MEDIA_SUBTITLE((long) 1 << 52),
    MEDIA_METADATA((long) 1 << 53),
    VIDEO_ENCODER(ENCODER.getValue() | MEDIA_VIDEO.getValue() | MEDIA_IMAGE.getValue()),
    AUDIO_ENCODER(ENCODER.getValue() | MEDIA_AUDIO.getValue()),
    AUDIOVIDEO_SINKS(SINK.getValue() | MEDIA_AUDIO.getValue() | MEDIA_VIDEO.getValue() | MEDIA_IMAGE.getValue()),
    DECODABLE(DECODER.getValue() | DEMUXER.getValue() | DEPAYLOADER.getValue() | PARSER.getValue());
    private final long value;

    private ListType(long value) {
      this.value = value;
    }

    public long getValue() {
      return value;
    }
  }
}
