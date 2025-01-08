/*
 * This file is part of gstreamer-java.
 * 
 * This code is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License version 3 only, as published by the Free Software Foundation.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License version 3 for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3 along with this work.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.freedesktop.gstreamer.elements;

import static org.freedesktop.gstreamer.lowlevel.AppAPI.APP_API;

import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.FlowReturn;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;

@SuppressWarnings("unused")
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
      public FlowReturn callback(AppSink elem) {
        return listener.newPreroll(elem);
      }
    });
  }

  public void disconnect(NEW_PREROLL listener) {
    disconnect(NEW_PREROLL.class, listener);
  }
}
