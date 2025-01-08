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

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Union;
import com.sun.jna.ptr.LongByReference;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.FlowReturn;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Pad;
import org.freedesktop.gstreamer.elements.BaseSrc;
import org.freedesktop.gstreamer.event.Event;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstSegmentStruct;
import org.freedesktop.gstreamer.lowlevel.GstElementAPI.GstElementClass;
import org.freedesktop.gstreamer.lowlevel.GstElementAPI.GstElementStruct;
import org.freedesktop.gstreamer.query.Query;

public interface BaseSrcAPI extends Library {
  BaseSrcAPI BASESRC_API = GstNative.load("gstbase", BaseSrcAPI.class);
  int GST_PADDING = GstAPI.GST_PADDING;
  int GST_PADDING_LARGE = GstAPI.GST_PADDING_LARGE;

  public static final class GstBaseSrcStruct extends com.sun.jna.Structure {
    public GstElementStruct element;
    public volatile Pad srcpad;
    public volatile Pointer live_lock;
    public volatile Pointer live_cond;
    public volatile boolean is_live;
    public volatile boolean live_running;
    public volatile int blocksize;
    public volatile boolean can_activate_push;
    public volatile boolean random_access;
    public volatile Pointer clock_id;
    public volatile GstSegmentStruct segment;
    public volatile boolean need_newsegment;
    public volatile int num_buffers;
    public volatile int num_buffers_left;
    public volatile boolean typefind;
    public volatile boolean running;
    public volatile Event pending_seek;
    public volatile Pointer priv;
    public volatile Pointer[] _gst_reserved = new Pointer[GST_PADDING_LARGE];

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"element", "srcpad", "live_lock", "live_cond", "is_live", "live_running", "blocksize", "can_activate_push", "random_access", "clock_id", "segment", "need_newsegment", "num_buffers", "num_buffers_left", "typefind", "running", "pending_seek", "priv", "_gst_reserved"});
    }
  }

  public static final class GstBaseSrcAbiData extends Union {
    public volatile GstBaseSrcAbi abi;
    public volatile Pointer[] _gst_reserved = new Pointer[GST_PADDING_LARGE - 1];
  }

  public static final class GstBaseSrcAbi extends com.sun.jna.Structure {
    public volatile boolean typefind;
    public volatile boolean running;
    public volatile Pointer pending_seek;

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"typefind", "running", "pending_seek"});
    }
  }

  public static interface GetCaps extends Callback {
    public Caps callback(BaseSrc src, Caps filter);
  }

  public static interface SetCaps extends Callback {
    public boolean callback(BaseSrc src, Caps caps);
  }

  public static interface BooleanFunc1 extends Callback {
    public boolean callback(BaseSrc src);
  }

  public static interface DecideAllocation extends Callback {
    public boolean callback(BaseSrc src, Query query);
  }

  public static interface GetTimes extends Callback {
    public void callback(BaseSrc src, Buffer buffer, Pointer start, Pointer end);
  }

  public static interface GetSize extends Callback {
    boolean callback(BaseSrc src, LongByReference size);
  }

  public static interface EventNotify extends Callback {
    boolean callback(BaseSrc src, Event event);
  }

  public static interface Create extends Callback {
    public FlowReturn callback(BaseSrc src, long offset, int size, Pointer bufRef);
  }

  public static interface Fill extends Callback {
    public FlowReturn callback(BaseSrc src, long offset, int size, Buffer buffer);
  }

  public static interface Seek extends Callback {
    boolean callback(BaseSrc src, GstSegmentStruct segment);
  }

  public static interface QueryFunc extends Callback {
    boolean callback(BaseSrc src, Query query);
  }

  public static interface Fixate extends Callback {
    public Caps callback(BaseSrc src, Caps caps);
  }

  public static interface PrepareSeek extends Callback {
    boolean callback(BaseSrc src, Event seek, GstSegmentStruct segment);
  }

  public static final class GstBaseSrcClass extends com.sun.jna.Structure {
    public GstBaseSrcClass() {}

    public GstBaseSrcClass(Pointer ptr) {
      useMemory(ptr);
      read();
    }

    public GstElementClass parent_class;
    public GetCaps get_caps;
    public BooleanFunc1 negotiate;
    public Fixate fixate;
    public SetCaps set_caps;
    public DecideAllocation decide_allocation;
    public BooleanFunc1 start;
    public BooleanFunc1 stop;
    public GetTimes get_times;
    public GetSize get_size;
    public BooleanFunc1 is_seekable;
    public PrepareSeek prepare_seek_segment;
    public Seek do_seek;
    public BooleanFunc1 unlock;
    public BooleanFunc1 unlock_stop;
    public QueryFunc query;
    public EventNotify event;
    public Create create;
    public Create alloc;
    public Fill fill;
    public volatile Pointer[] _gst_reserved = new Pointer[GST_PADDING_LARGE];

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"parent_class", "get_caps", "negotiate", "fixate", "set_caps", "decide_allocation", "start", "stop", "get_times", "get_size", "is_seekable", "prepare_seek_segment", "do_seek", "unlock", "unlock_stop", "query", "event", "create", "alloc", "fill", "_gst_reserved"});
    }
  }

  GType gst_base_src_get_type();

  FlowReturn gst_base_src_wait_playing(BaseSrc src);

  void gst_base_src_set_live(BaseSrc src, boolean live);

  boolean gst_base_src_is_live(BaseSrc src);

  void gst_base_src_set_format(BaseSrc src, Format format);

  boolean gst_base_src_query_latency(BaseSrc src, boolean[] live, long[] min_latency, long[] max_latency);

  void gst_base_src_set_blocksize(BaseSrc src, long blocksize);

  long gst_base_src_get_blocksize(BaseSrc src);

  void gst_base_src_set_do_timestamp(BaseSrc src, boolean timestamp);

  boolean gst_base_src_get_do_timestamp(BaseSrc src);

  boolean gst_base_src_new_seamless_segment(BaseSrc src, long start, long stop, long position);
}
