package org.freedesktop.gstreamer.video;

import java.util.EnumSet;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.NativeFlags;
import org.freedesktop.gstreamer.glib.NativeObject;
import org.freedesktop.gstreamer.lowlevel.GPointer;
import org.freedesktop.gstreamer.lowlevel.GstVideoAPI.GstVideoTimeCodeConfigStruct;

@Gst.Since(minor = 10)
public class VideoTimeCodeConfig extends NativeObject {
  private final GstVideoTimeCodeConfigStruct timeCodeConfig;

  VideoTimeCodeConfig(GstVideoTimeCodeConfigStruct struct) {
    this(struct, new Handle(new GPointer(struct.getPointer()), false));
  }

  private VideoTimeCodeConfig(GstVideoTimeCodeConfigStruct struct, Handle handle) {
    super(handle);
    timeCodeConfig = struct;
  }

  public EnumSet<VideoTimeCodeFlags> getFlags() {
    return NativeFlags.fromInt(VideoTimeCodeFlags.class, timeCodeConfig.flags);
  }

  public int getNumerator() {
    return timeCodeConfig.fps_n;
  }

  public int getDenominator() {
    return timeCodeConfig.fps_d;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("GstVideoTimeCodeConfig{");
    sb.append("flags=").append(getFlags()).append(", numerator=").append(getNumerator()).append(", denominator=").append(getDenominator()).append('}');
    return sb.toString();
  }

  private static final class Handle extends NativeObject.Handle {
    public Handle(GPointer ptr, boolean ownsReference) {
      super(ptr, ownsReference);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {}

    @Override
    protected GPointer getPointer() {
      return super.getPointer();
    }
  }
}
