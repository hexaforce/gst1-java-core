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

import org.freedesktop.gstreamer.glib.NativeFlags;

public enum BufferFlags implements NativeFlags<BufferFlags> {
  LIVE(MiniObjectFlags.LAST.intValue() << 0),
  DECODE_ONLY(MiniObjectFlags.LAST.intValue() << 1),
  DISCONT(MiniObjectFlags.LAST.intValue() << 2),
  RESYNC(MiniObjectFlags.LAST.intValue() << 3),
  CORRUPTED(MiniObjectFlags.LAST.intValue() << 4),
  MARKER(MiniObjectFlags.LAST.intValue() << 5),
  HEADER(MiniObjectFlags.LAST.intValue() << 6),
  GAP(MiniObjectFlags.LAST.intValue() << 7),
  DROPPABLE(MiniObjectFlags.LAST.intValue() << 8),
  DELTA_UNIT(MiniObjectFlags.LAST.intValue() << 9),
  TAG_MEMORY(MiniObjectFlags.LAST.intValue() << 10),
  SYNC_AFTER(MiniObjectFlags.LAST.intValue() << 11),
  @Gst.Since(minor = 14) NON_DROPPABLE(MiniObjectFlags.LAST.intValue() << 12),
  LAST(MiniObjectFlags.LAST.intValue() << 16);
  private final int value;

  private BufferFlags(int value) {
    this.value = value;
  }

  public final int intValue() {
    return value;
  }
}
