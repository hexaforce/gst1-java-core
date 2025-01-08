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

import org.freedesktop.gstreamer.glib.NativeEnum;

public enum LFOWaveform implements NativeEnum<LFOWaveform> {
  SINE(0),
  SQUARE(1),
  SAW(2),
  REVERSE_SAW(3),
  TRIANGLE(4);
  private final int value;

  private LFOWaveform(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}
