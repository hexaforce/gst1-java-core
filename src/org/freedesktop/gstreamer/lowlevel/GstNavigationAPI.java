package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Library;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.interfaces.Navigation;

public interface GstNavigationAPI extends Library {
  GstNavigationAPI GSTNAVIGATION_API = GstNative.load("gstvideo", GstNavigationAPI.class);

  GType gst_navigation_get_type();

  void gst_navigation_send_event(Navigation navigation, Structure structure);

  void gst_navigation_send_key_event(Navigation navigation, String event, String key);

  void gst_navigation_send_mouse_event(Navigation navigation, String event, int button, double x, double y);
}
