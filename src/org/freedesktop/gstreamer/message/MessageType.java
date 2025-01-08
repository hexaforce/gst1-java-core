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

package org.freedesktop.gstreamer.message;

import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.NativeEnum;

public enum MessageType implements NativeEnum<MessageType> {
  UNKNOWN(0),
  EOS(1 << 0),
  ERROR(1 << 1),
  WARNING(1 << 2),
  INFO(1 << 3),
  TAG(1 << 4),
  BUFFERING(1 << 5),
  STATE_CHANGED(1 << 6),
  STATE_DIRTY(1 << 7),
  STEP_DONE(1 << 8),
  CLOCK_PROVIDE(1 << 9),
  CLOCK_LOST(1 << 10),
  NEW_CLOCK(1 << 11),
  STRUCTURE_CHANGE(1 << 12),
  STREAM_STATUS(1 << 13),
  APPLICATION(1 << 14),
  ELEMENT(1 << 15),
  SEGMENT_START(1 << 16),
  SEGMENT_DONE(1 << 17),
  DURATION_CHANGED(1 << 18),
  LATENCY(1 << 19),
  ASYNC_START(1 << 20),
  ASYNC_DONE(1 << 21),
  REQUEST_STATE(1 << 22),
  STEP_START(1 << 23),
  QOS(1 << 24),
  PROGRESS(1 << 25),
  TOC(1 << 26),
  RESET_TIME(1 << 27),
  STREAM_START(1 << 28),
  NEED_CONTEXT(1 << 29),
  HAVE_CONTEXT(1 << 30),
  EXTENDED(1 << 31),
  DEVICE_ADDED(EXTENDED.intValue() + 1),
  DEVICE_REMOVED(EXTENDED.intValue() + 2),
  @Gst.Since(minor = 10) PROPERTY_NOTIFY(EXTENDED.intValue() + 3),
  @Gst.Since(minor = 10) STREAM_COLLECTION(EXTENDED.intValue() + 4),
  @Gst.Since(minor = 10) STREAMS_SELECTED(EXTENDED.intValue() + 5),
  @Gst.Since(minor = 10) REDIRECT(EXTENDED.intValue() + 6),
  @Gst.Since(minor = 16) DEVICE_CHANGED(EXTENDED.intValue() + 7),
  @Gst.Since(minor = 18) INSTANT_RATE_REQUEST(EXTENDED.intValue() + 8),
  ANY(~0);
  private final int type;

  private MessageType(int type) {
    this.type = type;
  }

  @Override
  public int intValue() {
    return type;
  }
}
