package org.freedesktop.gstreamer.interfaces;

import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.glib.GObject;
import org.freedesktop.gstreamer.lowlevel.GType;

class GstInterface implements GObject.GInterface {
  protected final Element element;

  protected GstInterface(Element element, GType type) {
    this.element = element;
  }

  @Override
  public Element getGObject() {
    return element;
  }
}
