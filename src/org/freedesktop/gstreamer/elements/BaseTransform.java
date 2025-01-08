package org.freedesktop.gstreamer.elements;

import org.freedesktop.gstreamer.Element;

public class BaseTransform extends Element {
  public static final String GTYPE_NAME = "GstBaseTransform";

  protected BaseTransform(Initializer init) {
    super(init);
  }
}
