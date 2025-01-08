package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Structure;
import java.util.Collections;
import java.util.List;

public class GValueStruct extends Structure {
  public volatile int g_type;

  public GValueStruct() {}

  @Override
  protected List<String> getFieldOrder() {
    return Collections.singletonList("g_type");
  }
}
