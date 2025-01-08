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

public enum MiniObjectFlags implements NativeFlags<MiniObjectFlags> {
  LOCKABLE(1 << 0),
  LOCK_READONLY(1 << 1),
  MAY_BE_LEAKED(1 << 2),
  LAST(1 << 4);
  private final int value;

  private MiniObjectFlags(int value) {
    this.value = value;
  }

  public int intValue() {
    return value;
  }
}
