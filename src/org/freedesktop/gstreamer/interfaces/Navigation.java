package org.freedesktop.gstreamer.interfaces;

import static org.freedesktop.gstreamer.lowlevel.GstNavigationAPI.GSTNAVIGATION_API;

import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.Structure;

public class Navigation extends GstInterface {
  public static final Navigation wrap(Element element) {
    return new Navigation(element);
  }

  private Navigation(Element element) {
    super(element, GSTNAVIGATION_API.gst_navigation_get_type());
  }

  public void sendEvent(Structure structure) {
    GSTNAVIGATION_API.gst_navigation_send_event(this, structure);
  }

  public void sendKeyEvent(String event, String key) {
    GSTNAVIGATION_API.gst_navigation_send_key_event(this, event, key);
  }

  public void sendMouseEvent(String event, int button, double x, double y) {
    GSTNAVIGATION_API.gst_navigation_send_mouse_event(this, event, button, x, y);
  }
}
