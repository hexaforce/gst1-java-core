package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.interfaces.ColorBalance;
import org.freedesktop.gstreamer.interfaces.ColorBalanceChannel;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;

public interface GstColorBalanceAPI extends Library {
  GstColorBalanceAPI GSTCOLORBALANCE_API = GstNative.load("gstvideo", GstColorBalanceAPI.class);

  GType gst_color_balance_channel_get_type();

  GType gst_color_balance_get_type();

  GList gst_color_balance_list_channels(ColorBalance balance);

  void gst_color_balance_set_value(ColorBalance balance, ColorBalanceChannel channel, int value);

  int gst_color_balance_get_value(ColorBalance balance, ColorBalanceChannel channel);

  public static final class ColorBalanceChannelStruct extends com.sun.jna.Structure {
    public volatile GObjectAPI.GObjectStruct parent;
    public volatile String label;
    public volatile int min_value;
    public volatile int max_value;

    public String getLabel() {
      return (String) readField("label");
    }

    public int getMinValue() {
      return (Integer) readField("min_value");
    }

    public int getMaxValue() {
      return (Integer) readField("max_value");
    }

    public void read() {}

    public void write() {}

    public ColorBalanceChannelStruct(Pointer ptr) {
      useMemory(ptr);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"parent", "label", "min_value", "max_value"});
    }
  }
}
