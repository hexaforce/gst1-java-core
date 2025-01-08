package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.ClockReturn;
import org.freedesktop.gstreamer.FlowReturn;
import org.freedesktop.gstreamer.MiniObject;
import org.freedesktop.gstreamer.Pad;
import org.freedesktop.gstreamer.PadMode;
import org.freedesktop.gstreamer.elements.BaseSink;
import org.freedesktop.gstreamer.event.Event;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstSegmentStruct;
import org.freedesktop.gstreamer.lowlevel.GstElementAPI.GstElementClass;
import org.freedesktop.gstreamer.lowlevel.GstElementAPI.GstElementStruct;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;
import org.freedesktop.gstreamer.query.AllocationQuery;
import org.freedesktop.gstreamer.query.Query;

public interface BaseSinkAPI extends Library {
  BaseSinkAPI BASESINK_API = GstNative.load("gstbase", BaseSinkAPI.class);
  int GST_PADDING = GstAPI.GST_PADDING;
  int GST_PADDING_LARGE = GstAPI.GST_PADDING_LARGE;

  public static final class GstBaseSinkStruct extends com.sun.jna.Structure {
    public GstElementStruct element;
    public volatile Pad sinkpad;
    public volatile PadMode pad_mode;
    public volatile long offset;
    public volatile boolean can_activate_pull;
    public volatile boolean can_activate_push;
    public volatile Pointer preroll_lock;
    public volatile Pointer preroll_cond;
    public volatile boolean eos;
    public volatile boolean need_preroll;
    public volatile boolean have_preroll;
    public volatile boolean playing_async;
    public volatile boolean have_newsegment;
    public volatile GstSegmentStruct segment;
    public volatile Pointer clock_id;
    public volatile boolean sync;
    public volatile boolean flushing;
    public volatile boolean running;
    public volatile long max_lateness;
    public volatile Pointer priv;
    public volatile Pointer[] _gst_reserved = new Pointer[GST_PADDING_LARGE];

    public GstBaseSinkStruct(Pointer handle) {
      super(handle);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"element", "sinkpad", "pad_mode", "offset", "can_activate_pull", "can_activate_push", "preroll_lock", "preroll_cond", "eos", "need_preroll", "have_preroll", "playing_async", "have_newsegment", "segment", "clock_id", "sync", "flushing", "running", "max_lateness", "priv", "_gst_reserved"});
    }
  }

  public static interface GetCaps extends Callback {
    public Caps callback(BaseSink sink, Caps caps);
  }

  public static interface SetCaps extends Callback {
    public boolean callback(BaseSink sink, Caps caps);
  }

  public static interface Fixate extends Callback {
    public Caps callback(BaseSink sink, Caps caps);
  }

  public static interface ActivatePull extends Callback {
    public boolean callback(BaseSink sink, boolean active);
  }

  public static interface GetTimes extends Callback {
    public void callback(BaseSink sink, Buffer buffer, Pointer start, Pointer end);
  }

  public static interface ProposeAllocation extends Callback {
    public boolean callback(BaseSink sink, AllocationQuery query);
  }

  public static interface BooleanFunc1 extends Callback {
    public boolean callback(BaseSink sink);
  }

  public static interface QueryNotify extends Callback {
    public boolean callback(BaseSink sink, Query query);
  }

  public static interface EventNotify extends Callback {
    public boolean callback(BaseSink sink, Event event);
  }

  public static interface WaitEventNotify extends Callback {
    public FlowReturn callback(BaseSink sink, Event event);
  }

  public static interface Render extends Callback {
    public FlowReturn callback(BaseSink sink, Buffer buffer);
  }

  public static interface RenderList extends Callback {
    public FlowReturn callback(BaseSink sink, GList bufferList);
  }

  public static final class GstBaseSinkClass extends com.sun.jna.Structure {
    public GstBaseSinkClass() {}

    public GstBaseSinkClass(Pointer ptr) {
      super(ptr);
    }

    public GstElementClass parent_class;
    public GetCaps get_caps;
    public SetCaps set_caps;
    public Fixate fixate;
    public ActivatePull activate_pull;
    public GetTimes get_times;
    public ProposeAllocation propose_allocation;
    public BooleanFunc1 start;
    public BooleanFunc1 stop;
    public BooleanFunc1 unlock;
    public BooleanFunc1 unlock_stop;
    public QueryNotify query;
    public EventNotify event;
    public WaitEventNotify wait_event;
    public Render prepare;
    public RenderList prepare_list;
    public Render preroll;
    public Render render;
    public RenderList render_list;
    public volatile Pointer[] _gst_reserved = new Pointer[GST_PADDING_LARGE];

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"parent_class", "get_caps", "set_caps", "fixate", "activate_pull", "get_times", "propose_allocation", "start", "stop", "unlock", "unlock_stop", "query", "event", "wait_event", "prepare", "prepare_list", "preroll", "render", "render_list", "_gst_reserved"});
    }
  }

  GType gst_base_sink_get_type();

  FlowReturn gst_base_sink_do_preroll(BaseSink sink, MiniObject obj);

  FlowReturn gst_base_sink_wait_preroll(BaseSink sink);

  void gst_base_sink_set_sync(BaseSink sink, boolean sync);

  boolean gst_base_sink_get_sync(BaseSink sink);

  void gst_base_sink_set_max_lateness(BaseSink sink, long max_lateness);

  long gst_base_sink_get_max_lateness(BaseSink sink);

  void gst_base_sink_set_qos_enabled(BaseSink sink, boolean enabled);

  boolean gst_base_sink_is_qos_enabled(BaseSink sink);

  void gst_base_sink_set_async_enabled(BaseSink sink, boolean enabled);

  boolean gst_base_sink_is_async_enabled(BaseSink sink);

  void gst_base_sink_set_ts_offset(BaseSink sink, long offset);

  long gst_base_sink_get_ts_offset(BaseSink sink);

  @CallerOwnsReturn Buffer gst_base_sink_get_last_buffer(BaseSink sink);

  void gst_base_sink_set_last_buffer_enabled(BaseSink sink, boolean enable);

  boolean gst_base_sink_is_last_buffer_enabled(BaseSink sink);

  boolean gst_base_sink_query_latency(BaseSink sink, boolean live, boolean upstream_live, long min_latency, long max_latency);

  long gst_base_sink_get_latency(BaseSink sink);

  void gst_base_sink_set_render_delay(BaseSink sink, long delay);

  long gst_base_sink_get_render_delay(BaseSink sink);

  void gst_base_sink_set_blocksize(BaseSink sink, int blocksize);

  int gst_base_sink_get_blocksize(BaseSink sink);

  ClockReturn gst_base_sink_wait_clock(BaseSink sink, long time, Pointer jitter);

  FlowReturn gst_base_sink_wait_eos(BaseSink sink, long time, Pointer jitter);
}
