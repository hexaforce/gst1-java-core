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

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstBufferPoolAPI;

public class BufferPool extends GstObject {
  public static final String GTYPE_NAME = "GstBufferPool";

  public BufferPool() {
    this(Natives.initializer(GstBufferPoolAPI.GSTBUFFERPOOL_API.ptr_gst_buffer_pool_new()));
  }

  BufferPool(final Initializer init) {
    super(init);
  }

  public void setParams(Caps caps, int size, int min_buffers, int max_buffers) {
    Structure config = GstBufferPoolAPI.GSTBUFFERPOOL_API.gst_buffer_pool_get_config(this);
    GstBufferPoolAPI.GSTBUFFERPOOL_API.gst_buffer_pool_config_set_params(config, caps, size, min_buffers, max_buffers);
  }

  public Caps getCaps() {
    Structure config = GstBufferPoolAPI.GSTBUFFERPOOL_API.gst_buffer_pool_get_config(this);
    Pointer[] ptr = new Pointer[1];
    GstBufferPoolAPI.GSTBUFFERPOOL_API.gst_buffer_pool_config_get_params(config, ptr, null, null, null);
    return new Caps(Natives.initializer(ptr[0], false, true));
  }
}
