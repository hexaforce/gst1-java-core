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

import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;

public enum PadLinkReturn implements NativeEnum<PadLinkReturn> {
  OK(0),
  WRONG_HIERARCHY(-1),
  WAS_LINKED(-2),
  WRONG_DIRECTION(-3),
  NOFORMAT(-4),
  NOSCHED(-5),
  @DefaultEnumValue REFUSED(-6);
  private final int value;

  PadLinkReturn(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
