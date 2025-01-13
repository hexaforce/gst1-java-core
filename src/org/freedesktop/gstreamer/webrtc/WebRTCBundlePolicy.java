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

package org.freedesktop.gstreamer.webrtc;

import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.NativeEnum;

@Gst.Since(minor = 14)
public enum WebRTCBundlePolicy implements NativeEnum<WebRTCBundlePolicy> {
  NONE(0),
  BALANCED(1),
  MAX_COMPAT(2),
  MAX_BUNDLE(3);

  private final int value;

  private WebRTCBundlePolicy(int value) {
    this.value = value;
  }

  @Override
  public int intValue() {
    return value;
  }
}