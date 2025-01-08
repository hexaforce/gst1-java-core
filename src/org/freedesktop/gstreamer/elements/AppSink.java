package org.freedesktop.gstreamer.elements;

import static org.freedesktop.gstreamer.lowlevel.AppAPI.APP_API;

import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.FlowReturn;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;

public class AppSink extends BaseSink {
  public static final String GST_NAME = "appsink";
  public static final String GTYPE_NAME = "GstAppSink";

  AppSink(Initializer init) {
    super(init);
  }

  public AppSink(String name) {
    this(makeRawElement(GST_NAME, name));
  }

  @Override
  public void setCaps(Caps caps) {
    APP_API.gst_app_sink_set_caps(this, caps);
  }

  public Caps getCaps() {
    return APP_API.gst_app_sink_get_caps(this);
  }

  public boolean isEOS() {
    return APP_API.gst_app_sink_is_eos(this);
  }

  public Sample pullPreroll() {
    return APP_API.gst_app_sink_pull_preroll(this);
  }

  public Sample pullSample() {
    return APP_API.gst_app_sink_pull_sample(this);
  }

  public static interface EOS {
    public void eos(AppSink elem);
  }

  public void connect(final EOS listener) {
    connect(EOS.class, listener, new GstCallback() {
      @SuppressWarnings("unused")
      public void callback(AppSink elem) {
        listener.eos(elem);
      }
    });
  }

  public void disconnect(EOS listener) {
    disconnect(EOS.class, listener);
  }

  public static interface NEW_SAMPLE {
    public FlowReturn newSample(AppSink elem);
  }

  public void connect(final NEW_SAMPLE listener) {
    connect(NEW_SAMPLE.class, listener, new GstCallback() {
      @SuppressWarnings("unused")
      public FlowReturn callback(AppSink elem) {
        return listener.newSample(elem);
      }
    });
  }

  public void disconnect(NEW_SAMPLE listener) {
    disconnect(NEW_SAMPLE.class, listener);
  }

  public static interface NEW_PREROLL {
    public FlowReturn newPreroll(AppSink elem);
  }

  public void connect(final NEW_PREROLL listener) {
    connect(NEW_PREROLL.class, listener, new GstCallback() {
      @SuppressWarnings("unused")
      public FlowReturn callback(AppSink elem) {
        return listener.newPreroll(elem);
      }
    });
  }

  public void disconnect(NEW_PREROLL listener) {
    disconnect(NEW_PREROLL.class, listener);
  }
}
