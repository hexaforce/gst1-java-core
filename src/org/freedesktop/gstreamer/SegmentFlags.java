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

import org.freedesktop.gstreamer.event.SeekFlags;
import org.freedesktop.gstreamer.glib.NativeFlags;

enum SegmentFlags implements NativeFlags<SegmentFlags> {
  RESET(SeekFlags.FLUSH),
  TRICKMODE(SeekFlags.TRICKMODE),
  SEGMENT(SeekFlags.SEGMENT),
  TRICKMODE_KEY_UNITS(SeekFlags.TRICKMODE_KEY_UNITS),
  TRICKMODE_NO_AUDIO(SeekFlags.TRICKMODE_NO_AUDIO);
  private final SeekFlags seekFlags;

  private SegmentFlags(SeekFlags seekFlags) {
    this.seekFlags = seekFlags;
  }

  @Override
  public int intValue() {
    return seekFlags.intValue();
  }
}
