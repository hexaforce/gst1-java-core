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

import java.util.Collections;
import java.util.Set;
import org.freedesktop.gstreamer.glib.NativeFlags;
import org.freedesktop.gstreamer.lowlevel.GstPadAPI;

public enum PadProbeType implements NativeFlags<PadProbeType> {
  IDLE(GstPadAPI.GST_PAD_PROBE_TYPE_IDLE),
  BLOCK(GstPadAPI.GST_PAD_PROBE_TYPE_BLOCK),
  BUFFER(GstPadAPI.GST_PAD_PROBE_TYPE_BUFFER),
  BUFFER_LIST(GstPadAPI.GST_PAD_PROBE_TYPE_BUFFER_LIST),
  EVENT_DOWNSTREAM(GstPadAPI.GST_PAD_PROBE_TYPE_EVENT_DOWNSTREAM),
  EVENT_UPSTREAM(GstPadAPI.GST_PAD_PROBE_TYPE_EVENT_UPSTREAM),
  EVENT_FLUSH(GstPadAPI.GST_PAD_PROBE_TYPE_EVENT_FLUSH),
  QUERY_DOWNSTREAM(GstPadAPI.GST_PAD_PROBE_TYPE_QUERY_DOWNSTREAM),
  QUERY_UPSTREAM(GstPadAPI.GST_PAD_PROBE_TYPE_QUERY_UPSTREAM),
  PUSH(GstPadAPI.GST_PAD_PROBE_TYPE_PUSH),
  PULL(GstPadAPI.GST_PAD_PROBE_TYPE_PULL);
  public static final Set<PadProbeType> BLOCKING = Collections.unmodifiableSet(NativeFlags.fromInt(PadProbeType.class, GstPadAPI.GST_PAD_PROBE_TYPE_BLOCKING));
  public static final Set<PadProbeType> DATA_DOWNSTREAM = Collections.unmodifiableSet(NativeFlags.fromInt(PadProbeType.class, GstPadAPI.GST_PAD_PROBE_TYPE_DATA_DOWNSTREAM));
  public static final Set<PadProbeType> DATA_UPSTREAM = Collections.unmodifiableSet(NativeFlags.fromInt(PadProbeType.class, GstPadAPI.GST_PAD_PROBE_TYPE_DATA_UPSTREAM));
  public static final Set<PadProbeType> DATA_BOTH = Collections.unmodifiableSet(NativeFlags.fromInt(PadProbeType.class, GstPadAPI.GST_PAD_PROBE_TYPE_DATA_BOTH));
  public static final Set<PadProbeType> BLOCK_DOWNSTREAM = Collections.unmodifiableSet(NativeFlags.fromInt(PadProbeType.class, GstPadAPI.GST_PAD_PROBE_TYPE_BLOCK_DOWNSTREAM));
  public static final Set<PadProbeType> BLOCK_UPSTREAM = Collections.unmodifiableSet(NativeFlags.fromInt(PadProbeType.class, GstPadAPI.GST_PAD_PROBE_TYPE_BLOCK_UPSTREAM));
  public static final Set<PadProbeType> EVENT_BOTH = Collections.unmodifiableSet(NativeFlags.fromInt(PadProbeType.class, GstPadAPI.GST_PAD_PROBE_TYPE_EVENT_BOTH));
  public static final Set<PadProbeType> QUERY_BOTH = Collections.unmodifiableSet(NativeFlags.fromInt(PadProbeType.class, GstPadAPI.GST_PAD_PROBE_TYPE_QUERY_BOTH));
  public static final Set<PadProbeType> ALL_BOTH = Collections.unmodifiableSet(NativeFlags.fromInt(PadProbeType.class, GstPadAPI.GST_PAD_PROBE_TYPE_ALL_BOTH));
  public static final Set<PadProbeType> SCHEDULING = Collections.unmodifiableSet(NativeFlags.fromInt(PadProbeType.class, GstPadAPI.GST_PAD_PROBE_TYPE_SCHEDULING));
  private final int value;

  private PadProbeType(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
