package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstPadAPI.GSTPAD_API;

import java.util.Set;
import org.freedesktop.gstreamer.event.Event;
import org.freedesktop.gstreamer.glib.NativeFlags;
import org.freedesktop.gstreamer.lowlevel.GstPadProbeInfo;
import org.freedesktop.gstreamer.query.Query;

public final class PadProbeInfo {
  private GstPadProbeInfo info;

  PadProbeInfo(GstPadProbeInfo info) {
    this.info = info;
  }

  public Set<PadProbeType> getType() {
    return NativeFlags.fromInt(PadProbeType.class, info.padProbeType);
  }

  public Buffer getBuffer() {
    return GSTPAD_API.gst_pad_probe_info_get_buffer(info);
  }

  public Event getEvent() {
    return GSTPAD_API.gst_pad_probe_info_get_event(info);
  }

  public Query getQuery() {
    return GSTPAD_API.gst_pad_probe_info_get_query(info);
  }

  void invalidate() {
    info = null;
  }
}
