package org.freedesktop.gstreamer.elements;

import org.freedesktop.gstreamer.Element;

public class BaseSrc extends Element {
  public static final String GTYPE_NAME = "GstBaseSrc";

  protected BaseSrc(Initializer init) {
    super(init);
  }
}
