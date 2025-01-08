package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

public interface GstAPI extends Library {
  GstAPI GST_API = GstNative.load(GstAPI.class);
  int GST_PADDING = 4;
  int GST_PADDING_LARGE = 20;

  GType gst_type_find_get_type();

  @CallerOwnsReturn String gst_version_string();

  void gst_version(long[] major, long[] minor, long[] micro, long[] nano);

  boolean gst_init(IntByReference argc, PointerByReference argv);

  boolean gst_init_check(IntByReference argc, PointerByReference argv, Pointer[] err);

  boolean gst_init_check(IntByReference argc, PointerByReference argv, GErrorStruct[] err);

  boolean gst_segtrap_is_enabled();

  void gst_segtrap_set_enabled(boolean enabled);

  void gst_deinit();

  public static final class GstSegmentStruct extends com.sun.jna.Structure {
    public GstSegmentStruct() {}

    public GstSegmentStruct(Pointer p) {
      super(p);
    }

    public GstSegmentStruct(int flags, double rate, double applied_rate, Format format, long base, long offset, long start, long stop, long time, long position, long duration) {
      this.flags = flags;
      this.rate = rate;
      this.applied_rate = applied_rate;
      this.format = format;
      this.base = base;
      this.offset = offset;
      this.start = start;
      this.stop = stop;
      this.time = time;
      this.position = position;
      this.duration = duration;
    }

    public int flags;
    public double rate;
    public double applied_rate;
    public Format format;
    public long base;
    public long offset;
    public long start;
    public long stop;
    public long time;
    public long position;
    public long duration;
    public volatile Pointer[] _gst_reserved = new Pointer[GST_PADDING];

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"flags", "rate", "applied_rate", "format", "base", "offset", "start", "stop", "time", "position", "duration", "_gst_reserved"});
    }
  };

  public static final class GErrorStruct extends com.sun.jna.Structure {
    public volatile int domain;
    public volatile int code;
    public volatile String message;

    public GErrorStruct() {
      clear();
    }

    public GErrorStruct(Pointer ptr) {
      useMemory(ptr);
    }

    public int getCode() {
      return (Integer) readField("code");
    }

    public String getMessage() {
      return (String) readField("message");
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"domain", "code", "message"});
    }
  }

  public static interface GstCallback extends com.sun.jna.Callback {
    static final com.sun.jna.TypeMapper TYPE_MAPPER = new GTypeMapper();
  }
}
