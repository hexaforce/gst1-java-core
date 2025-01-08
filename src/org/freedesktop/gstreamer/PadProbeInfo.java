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
