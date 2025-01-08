package org.freedesktop.gstreamer.video;

import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.NativeObject;
import org.freedesktop.gstreamer.lowlevel.GPointer;
import org.freedesktop.gstreamer.lowlevel.GstVideoAPI;
import org.freedesktop.gstreamer.lowlevel.GstVideoAPI.GstVideoTimeCodeStruct;

@Gst.Since(minor = 10)
public class VideoTimeCode extends NativeObject {
  private final GstVideoTimeCodeStruct timeCodeStruct;
  private final VideoTimeCodeConfig timeCodeConfig;

  VideoTimeCode(GstVideoTimeCodeStruct struct) {
    this(struct, new Handle(new GPointer(struct.getPointer()), false));
  }

  private VideoTimeCode(GstVideoTimeCodeStruct struct, Handle handle) {
    super(handle);
    this.timeCodeStruct = struct;
    timeCodeConfig = new VideoTimeCodeConfig(timeCodeStruct.config);
  }

  public VideoTimeCodeConfig getConfig() {
    return timeCodeConfig;
  }

  public int getHours() {
    return timeCodeStruct.hours;
  }

  public int getMinutes() {
    return timeCodeStruct.minutes;
  }

  public int getSeconds() {
    return timeCodeStruct.seconds;
  }

  public int getFrames() {
    return timeCodeStruct.frames;
  }

  @Override
  public String toString() {
    return "GstVideoTimeCode{" + getHours() + ":" + getMinutes() + ":" + getSeconds() + ":" + getFrames() + ", timeconfig=" + timeCodeConfig + "}";
  }

  @Override
  public void disown() {
    timeCodeConfig.disown();
    super.disown();
  }

  private static final class Handle extends NativeObject.Handle {
    public Handle(GPointer ptr, boolean ownsReference) {
      super(ptr, ownsReference);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {
      GstVideoAPI.GSTVIDEO_API.gst_video_time_code_free(ptr.getPointer());
    }

    @Override
    protected GPointer getPointer() {
      return super.getPointer();
    }
  }
}
