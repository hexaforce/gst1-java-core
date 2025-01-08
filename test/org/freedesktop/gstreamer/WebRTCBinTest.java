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

import static org.junit.Assert.assertEquals;

import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.webrtc.WebRTCBundlePolicy;
import org.freedesktop.gstreamer.webrtc.WebRTCICEGatheringState;
import org.freedesktop.gstreamer.webrtc.WebRTCPeerConnectionState;
import org.junit.Test;

public class WebRTCBinTest {
  @Test
  public void bundlePolicyTest() {
    assertEquals(NativeEnum.fromInt(WebRTCBundlePolicy.class, 0), WebRTCBundlePolicy.NONE);
    assertEquals(NativeEnum.fromInt(WebRTCBundlePolicy.class, 1), WebRTCBundlePolicy.BALANCED);
    assertEquals(NativeEnum.fromInt(WebRTCBundlePolicy.class, 2), WebRTCBundlePolicy.MAX_COMPAT);
    assertEquals(NativeEnum.fromInt(WebRTCBundlePolicy.class, 3), WebRTCBundlePolicy.MAX_BUNDLE);
  }

  @Test
  public void connectionStateTest() {
    assertEquals(NativeEnum.fromInt(WebRTCPeerConnectionState.class, 0), WebRTCPeerConnectionState.NEW);
    assertEquals(NativeEnum.fromInt(WebRTCPeerConnectionState.class, 1), WebRTCPeerConnectionState.CONNECTING);
    assertEquals(NativeEnum.fromInt(WebRTCPeerConnectionState.class, 2), WebRTCPeerConnectionState.CONNECTED);
    assertEquals(NativeEnum.fromInt(WebRTCPeerConnectionState.class, 3), WebRTCPeerConnectionState.DISCONNECTED);
    assertEquals(NativeEnum.fromInt(WebRTCPeerConnectionState.class, 4), WebRTCPeerConnectionState.FAILED);
    assertEquals(NativeEnum.fromInt(WebRTCPeerConnectionState.class, 5), WebRTCPeerConnectionState.CLOSED);
  }

  @Test
  public void iceGatheringStateTest() {
    assertEquals(NativeEnum.fromInt(WebRTCICEGatheringState.class, 0), WebRTCICEGatheringState.NEW);
    assertEquals(NativeEnum.fromInt(WebRTCICEGatheringState.class, 1), WebRTCICEGatheringState.GATHERING);
    assertEquals(NativeEnum.fromInt(WebRTCICEGatheringState.class, 2), WebRTCICEGatheringState.COMPLETE);
  }
}
