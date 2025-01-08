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

package org.freedesktop.gstreamer.lowlevel;

import static org.freedesktop.gstreamer.lowlevel.GstAPI.GST_PADDING;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.lowlevel.GstMiniObjectAPI.MiniObjectStruct;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

public interface GstBufferAPI extends com.sun.jna.Library {
  GstBufferAPI GSTBUFFER_API = GstNative.load(GstBufferAPI.class);
  public static final int GST_LOCK_FLAG_READ = (1 << 0);
  public static final int GST_LOCK_FLAG_WRITE = (1 << 1);
  public static final int GST_MAP_READ = GST_LOCK_FLAG_READ;
  public static final int GST_MAP_WRITE = GST_LOCK_FLAG_WRITE;

  public static final class MapInfoStruct extends com.sun.jna.Structure {
    public volatile Pointer memory;
    public volatile int flags;
    public volatile Pointer data;
    public volatile NativeLong size;
    public volatile NativeLong maxSize;
    public volatile Pointer[] user_data = new Pointer[4];
    public volatile Pointer[] _gst_reserved = new Pointer[GST_PADDING];

    public MapInfoStruct() {}

    public MapInfoStruct(Pointer ptr) {
      super(ptr);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"memory", "flags", "data", "size", "maxSize", "user_data", "_gst_reserved"});
    }
  }

  GType gst_buffer_get_type();

  @CallerOwnsReturn Buffer gst_buffer_new();

  @CallerOwnsReturn Buffer gst_buffer_new_allocate(Pointer allocator, int size, Pointer params);

  @CallerOwnsReturn Pointer ptr_gst_buffer_new();

  @CallerOwnsReturn Pointer ptr_gst_buffer_new_allocate(Pointer allocator, int size, Pointer params);

  NativeLong gst_buffer_get_size(Buffer buffer);

  boolean gst_buffer_map(Buffer buffer, MapInfoStruct info, int flags);

  void gst_buffer_unmap(Buffer buffer, MapInfoStruct info);

  int gst_buffer_n_memory(Buffer buffer);

  boolean gst_buffer_map_range(Buffer buffer, int idx, int length, MapInfoStruct info, int flags);

  GstMetaPtr gst_buffer_get_meta(Buffer buffer, GType gType);

  int gst_buffer_get_n_meta(Buffer buffer, GType gType);

  GstMetaPtr gst_buffer_iterate_meta(Buffer buffer, PointerByReference state);

  int gst_buffer_get_flags(Buffer buffer);

  boolean gst_buffer_set_flags(Buffer buffer, int flags);

  boolean gst_buffer_unset_flags(Buffer buffer, int flags);

  public static final class BufferStruct extends com.sun.jna.Structure {
    public volatile MiniObjectStruct mini_object;
    public Pointer pool;
    public long pts;
    public long dts;
    public long duration;
    public long offset;
    public long offset_end;

    public BufferStruct(Pointer ptr) {
      super(ptr);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"mini_object", "pool", "pts", "dts", "duration", "offset", "offset_end"});
    }

    @Override
    public String toString() {
      return super.toString() + " " + pts + " " + dts + " " + duration;
    }
  }
}
