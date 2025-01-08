package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface GstControlSourceAPI extends Library {
  GstControlSourceAPI GSTCONTROLSOURCE_API = GstNative.load(GstControlSourceAPI.class);

  boolean gst_control_source_get_value(GstControlSourcePtr self, long timestamp, double[] value);

  boolean gst_control_source_get_value_array(GstControlSourcePtr self, long timestamp, long interval, int n_values, double[] values);

  @Structure.FieldOrder({"timestamp", "value"})
  public static final class GstTimedValue extends Structure {
    public volatile long timestamp;
    public volatile double value;

    public GstTimedValue() {
      super();
    }

    public GstTimedValue(Pointer ptr) {
      super(ptr);
    }
  }
}
