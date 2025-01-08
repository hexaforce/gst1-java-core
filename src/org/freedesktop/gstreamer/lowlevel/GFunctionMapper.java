package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.NativeLibrary;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class GFunctionMapper implements com.sun.jna.FunctionMapper {
  private static final List<String> stripPrefixes = Arrays.asList("ptr_");

  public String getFunctionName(NativeLibrary library, Method method) {
    String name = method.getName();
    for (String prefix : stripPrefixes) {
      if (name.startsWith(prefix)) {
        return name.substring(prefix.length());
      }
    }
    return name;
  }
}
