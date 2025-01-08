package org.freedesktop.gstreamer.util;

import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.Gst;
import org.junit.Assume;

public class TestAssumptions {
  public static void requireGstVersion(int major, int minor) {
    Assume.assumeTrue(Gst.testVersion(major, minor));
  }

  public static void requireElement(String elementType) {
    ElementFactory factory = null;
    try {
      factory = ElementFactory.find(elementType);
    } catch (Exception ex) {
    }
    Assume.assumeNotNull(factory);
  }
}
