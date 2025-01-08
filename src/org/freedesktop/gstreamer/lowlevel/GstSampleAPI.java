package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstSegmentStruct;
import org.freedesktop.gstreamer.lowlevel.GstMiniObjectAPI.MiniObjectStruct;

public interface GstSampleAPI extends com.sun.jna.Library {
  GstSampleAPI GSTSAMPLE_API = GstNative.load(GstSampleAPI.class);

  public static final class SampleStruct extends com.sun.jna.Structure {
    public volatile MiniObjectStruct mini_object;
    public volatile Pointer buffer;
    public volatile Pointer caps;
    public volatile GstSegmentStruct segment;
    public volatile Pointer info;
    public volatile Pointer buffer_list;

    public SampleStruct() {}

    public SampleStruct(Pointer ptr) {
      useMemory(ptr);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"mini_object", "buffer", "caps", "segment", "info", "buffer_list"});
    }
  }

  Caps gst_sample_get_caps(Sample sample);

  Buffer gst_sample_get_buffer(Sample sample);

  void gst_sample_set_buffer(Sample sample, Buffer buffer);

  void gst_sample_set_caps(Sample sample, Caps caps);
}
