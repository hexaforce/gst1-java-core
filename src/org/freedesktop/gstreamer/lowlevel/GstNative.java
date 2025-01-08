package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Library;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public final class GstNative {
  private static final String[] nameFormats = System.getProperty("gstreamer.GstNative.nameFormats", "%s-1.0").split("\\|");

  private GstNative() {}

  private static final Map<String, Object> options = new HashMap<String, Object>() {
    {
      put(Library.OPTION_TYPE_MAPPER, new GTypeMapper());
      put(Library.OPTION_FUNCTION_MAPPER, new GFunctionMapper());
    }
  };

  public static <T extends Library> T load(Class<T> interfaceClass) {
    return load("gstreamer", interfaceClass);
  }

  public static <T extends Library> T load(String libraryName, Class<T> interfaceClass) {
    for (String format : nameFormats) try {
        return GNative.loadLibrary(String.format(format, libraryName), interfaceClass, options);
      } catch (UnsatisfiedLinkError ex) {
        continue;
      }
    throw new UnsatisfiedLinkError("Could not load library: " + libraryName);
  }
}
