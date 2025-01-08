package org.freedesktop.gstreamer.elements;

import static org.freedesktop.gstreamer.lowlevel.AppAPI.APP_API;

import com.sun.jna.ptr.LongByReference;
import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.FlowReturn;
import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;

public class AppSrc extends BaseSrc {
  public static final String GST_NAME = "appsrc";
  public static final String GTYPE_NAME = "GstAppSrc";

  AppSrc(Initializer init) {
    super(init);
  }

  public AppSrc(String name) {
    this(makeRawElement(GST_NAME, name));
  }

  @Override
  public void setCaps(Caps caps) {
    APP_API.gst_app_src_set_caps(this, caps);
  }

  public Caps getCaps() {
    return APP_API.gst_app_src_get_caps(this);
  }

  public void setSize(long size) {
    APP_API.gst_app_src_set_size(this, size);
  }

  public long getSize() {
    return APP_API.gst_app_src_get_size(this);
  }

  public void setStreamType(AppSrc.StreamType type) {
    APP_API.gst_app_src_set_stream_type(this, type);
  }

  public AppSrc.StreamType getStreamType() {
    return APP_API.gst_app_src_get_stream_type(this);
  }

  public void setMaxBytes(long max) {
    APP_API.gst_app_src_set_max_bytes(this, max);
  }

  public long getMaxBytes() {
    return APP_API.gst_app_src_get_max_bytes(this);
  }

  public void setLatency(long min, long max) {
    APP_API.gst_app_src_set_latency(this, min, max);
  }

  public long[] getLatency() {
    LongByReference minRef = new LongByReference();
    LongByReference maxRef = new LongByReference();
    APP_API.gst_app_src_get_latency(this, minRef, minRef);
    return new long[] {minRef.getValue(), maxRef.getValue()};
  }

  public FlowReturn pushBuffer(Buffer buffer) {
    return APP_API.gst_app_src_push_buffer(this, buffer);
  }

  public FlowReturn endOfStream() {
    return APP_API.gst_app_src_end_of_stream(this);
  }

  public static interface ENOUGH_DATA {
    public void enoughData(AppSrc elem);
  }

  public void connect(final ENOUGH_DATA listener) {
    connect(ENOUGH_DATA.class, listener, new GstCallback() {
      @SuppressWarnings("unused")
      public void callback(AppSrc elem) {
        listener.enoughData(elem);
      }
    });
  }

  public void disconnect(ENOUGH_DATA listener) {
    disconnect(ENOUGH_DATA.class, listener);
  }

  public static interface NEED_DATA {
    public void needData(AppSrc elem, int size);
  }

  public void connect(final NEED_DATA listener) {
    connect(NEED_DATA.class, listener, new GstCallback() {
      @SuppressWarnings("unused")
      public void callback(AppSrc elem, int size) {
        listener.needData(elem, size);
      }
    });
  }

  public void disconnect(NEED_DATA listener) {
    disconnect(NEED_DATA.class, listener);
  }

  public static interface SEEK_DATA {
    public boolean seekData(AppSrc elem, long position);
  }

  public void connect(final SEEK_DATA listener) {
    connect(SEEK_DATA.class, listener, new GstCallback() {
      @SuppressWarnings("unused")
      public boolean callback(AppSrc elem, long position) {
        return listener.seekData(elem, position);
      }
    });
  }

  public void disconnect(SEEK_DATA listener) {
    disconnect(SEEK_DATA.class, listener);
  }

  public enum StreamType implements NativeEnum<StreamType> {
    STREAM(0),
    SEEKABLE(1),
    RANDOM_ACCESS(2);
    private final int value;

    private StreamType(int value) {
      this.value = value;
    }

    @Override
    public int intValue() {
      return value;
    }
  }
}
