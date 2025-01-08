package org.freedesktop.gstreamer.lowlevel;

import static org.freedesktop.gstreamer.lowlevel.GstAPI.GST_PADDING;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class GstPadProbeInfo extends Structure {
  public volatile int padProbeType;
  public volatile NativeLong id;
  public volatile Pointer data;
  public volatile long offset;
  public volatile int size;
  public volatile Pointer[] _gst_reserved = new Pointer[GST_PADDING];

  @Override
  protected List<String> getFieldOrder() {
    return Arrays.asList(new String[] {"padProbeType", "id", "data", "offset", "size", "_gst_reserved"});
  }
}
