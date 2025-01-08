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

package org.freedesktop.gstreamer.controller;

import static org.freedesktop.gstreamer.lowlevel.GstControllerAPI.GSTCONTROLLER_API;

import org.freedesktop.gstreamer.ControlSource;
import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.GstLFOControlSourcePtr;

public class LFOControlSource extends ControlSource {
  public static final String GTYPE_NAME = "GstLFOControlSource";

  public LFOControlSource() {
    this(new Handle(GSTCONTROLLER_API.gst_lfo_control_source_new(), true), false);
  }

  LFOControlSource(Initializer init) {
    this(new Handle(init.ptr.as(GstLFOControlSourcePtr.class, GstLFOControlSourcePtr::new), init.ownsHandle), init.needRef);
  }

  private LFOControlSource(Handle handle, boolean needRef) {
    super(handle, needRef);
  }

  public LFOControlSource setAmplitude(double value) {
    set("amplitude", value);
    return this;
  }

  public double getAmplitude() {
    Object val = get("amplitude");
    if (val instanceof Double) {
      return (double) val;
    }
    return 1;
  }

  public LFOControlSource setFrequency(double value) {
    set("frequency", value);
    return this;
  }

  public double getFrequency() {
    Object val = get("frequency");
    if (val instanceof Double) {
      return (double) val;
    }
    return 1;
  }

  public LFOControlSource setOffset(double value) {
    set("offset", value);
    return this;
  }

  public double getOffset() {
    Object val = get("offset");
    if (val instanceof Double) {
      return (double) val;
    }
    return 1;
  }

  public LFOControlSource setTimeshift(long value) {
    set("timeshift", value);
    return this;
  }

  public long getTimeshift() {
    Object val = get("timeshift");
    if (val instanceof Long) {
      return (long) val;
    }
    return 1;
  }

  public LFOControlSource setWaveform(LFOWaveform value) {
    set("waveform", value.intValue());
    return this;
  }

  public LFOWaveform getWaveform() {
    Object val = get("waveform");
    if (val instanceof Integer) {
      int nativeInt = (Integer) val;
      return NativeEnum.fromInt(LFOWaveform.class, nativeInt);
    }
    return LFOWaveform.SINE;
  }

  private static class Handle extends ControlSource.Handle {
    public Handle(GstLFOControlSourcePtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }
  }
}
