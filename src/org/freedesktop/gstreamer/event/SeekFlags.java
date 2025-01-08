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

package org.freedesktop.gstreamer.event;

import org.freedesktop.gstreamer.glib.NativeFlags;

public enum SeekFlags implements NativeFlags<SeekFlags> {
  FLUSH(1 << 0),
  ACCURATE(1 << 1),
  KEY_UNIT(1 << 2),
  SEGMENT(1 << 3),
  TRICKMODE(1 << 4),
  SNAP_BEFORE(1 << 5),
  SNAP_AFTER(1 << 6),
  TRICKMODE_KEY_UNITS(1 << 7),
  TRICKMODE_NO_AUDIO(1 << 8);
  private final int value;

  private SeekFlags(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
