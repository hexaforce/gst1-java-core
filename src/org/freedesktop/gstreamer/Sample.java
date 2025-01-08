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

import static org.freedesktop.gstreamer.lowlevel.GstSampleAPI.GSTSAMPLE_API;

public class Sample extends MiniObject {
  public static final String GTYPE_NAME = "GstSample";

  Sample(Initializer init) {
    super(init);
  }

  public Caps getCaps() {
    return GSTSAMPLE_API.gst_sample_get_caps(this);
  }

  @Gst.Since(minor = 16)
  public void setCaps(Caps caps) {
    Gst.checkVersion(1, 16);
    GSTSAMPLE_API.gst_sample_set_caps(this, caps);
  }

  public Buffer getBuffer() {
    return GSTSAMPLE_API.gst_sample_get_buffer(this);
  }

  @Gst.Since(minor = 16)
  public void setBuffer(Buffer buffer) {
    Gst.checkVersion(1, 16);
    GSTSAMPLE_API.gst_sample_set_buffer(this, buffer);
  }
}
