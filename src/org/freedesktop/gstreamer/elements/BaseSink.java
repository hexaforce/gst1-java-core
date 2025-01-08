package org.freedesktop.gstreamer.elements;

import org.freedesktop.gstreamer.Element;

public class BaseSink extends Element {
  public static final String GTYPE_NAME = "GstBaseSink";

  protected BaseSink(Initializer init) {
    super(init);
  }
}
