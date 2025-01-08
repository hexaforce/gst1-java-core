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

import static org.freedesktop.gstreamer.lowlevel.GstSDPMessageAPI.GSTSDPMESSAGE_API;

import com.sun.jna.Pointer;
import java.nio.charset.StandardCharsets;
import org.freedesktop.gstreamer.glib.NativeObject;
import org.freedesktop.gstreamer.lowlevel.GPointer;

public class SDPMessage extends NativeObject {
  public static final String GTYPE_NAME = "GstSDPMessage";

  SDPMessage(Initializer init) {
    this(new Handle(init.ptr, init.ownsHandle));
  }

  SDPMessage(Handle handle) {
    super(handle);
  }

  public SDPMessage() {
    this(initHandle());
  }

  public String toString() {
    return GSTSDPMESSAGE_API.gst_sdp_message_as_text(this);
  }

  public void parseBuffer(String sdpString) {
    byte[] data = sdpString.getBytes(StandardCharsets.US_ASCII);
    int length = sdpString.length();
    GSTSDPMESSAGE_API.gst_sdp_message_parse_buffer(data, length, this);
  }

  private static Handle initHandle() {
    Pointer[] ptr = new Pointer[1];
    GSTSDPMESSAGE_API.gst_sdp_message_new(ptr);
    return new Handle(new GPointer(ptr[0]), true);
  }

  private static final class Handle extends NativeObject.Handle {
    public Handle(GPointer ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {
      GSTSDPMESSAGE_API.gst_sdp_message_free(ptr.getPointer());
    }
  }
}
