/*
 * This file is part of gstreamer-java.
 * 
 * This code is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License version 3 only, as published by the Free Software Foundation.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License version 3 for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3 along with this work.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GType;

public class NavigationEvent extends Event {
  NavigationEvent(Initializer init) {
    super(init);
  }

  public NavigationEvent(Structure structure) {
    this(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_navigation(structure)));
  }

  @Override
  public String toString() {
    Structure s = getStructure();
    String event = s.getString("event");
    if (event.startsWith("key-")) {
      return String.format("%s: [key=%s]", event, s.getString("key"));
    } else if (event.startsWith("mouse-")) {
      return String.format("%s: [x=%f, y=%f button=%x]", event, s.getDouble("pointer_x"), s.getDouble("pointer_y"), s.getInteger("button"));
    } else {
      return String.format("%s", s.getString("event"));
    }
  }

  public static NavigationEvent createMouseEvent(String event, double x, double y, int button) {
    return new MouseEvent(event, x, y, button);
  }

  public static NavigationEvent createMouseMoveEvent(double x, double y, int button) {
    return createMouseEvent("mouse-move", x, y, button);
  }

  public static NavigationEvent createMouseButtonPressEvent(double x, double y, int button) {
    return createMouseEvent("mouse-button-press", x, y, button);
  }

  public static NavigationEvent createMouseButtonReleaseEvent(double x, double y, int button) {
    return createMouseEvent("mouse-button-release", x, y, button);
  }

  public static NavigationEvent createKeyEvent(String event, String key) {
    return new KeyEvent(event, key);
  }

  public static NavigationEvent createKeyPressEvent(String key) {
    return createKeyEvent("key-press", key);
  }

  public static NavigationEvent createKeyReleaseEvent(String key) {
    return createKeyEvent("key-release", key);
  }

  private static final class MouseEvent extends NavigationEvent {
    public MouseEvent(String event, double x, double y, int button) {
      super(new Structure("application/x-gst-navigation", "event", GType.STRING, event, "button", GType.INT, button, "pointer_x", GType.DOUBLE, x, "pointer_y", GType.DOUBLE, y));
    }

    @Override
    public String toString() {
      Structure s = getStructure();
      return String.format("%s: [x=%f, y=%f button=%x]", s.getString("event"), s.getDouble("pointer_x"), s.getDouble("pointer_y"), s.getInteger("button"));
    }
  }

  private static final class KeyEvent extends NavigationEvent {
    public KeyEvent(String event, String key) {
      super(new Structure("application/x-gst-navigation", "event", GType.STRING, event, "key", GType.STRING, key));
    }

    @Override
    public String toString() {
      Structure s = getStructure();
      return String.format("%s: [key=%s]", s.getString("event"), s.getString("key"));
    }
  }
}
