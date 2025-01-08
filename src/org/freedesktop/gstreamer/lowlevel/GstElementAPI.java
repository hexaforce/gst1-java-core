package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Clock;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Pad;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.StateChangeReturn;
import org.freedesktop.gstreamer.event.Event;
import org.freedesktop.gstreamer.event.SeekType;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;
import org.freedesktop.gstreamer.lowlevel.GstObjectAPI.GstObjectClass;
import org.freedesktop.gstreamer.lowlevel.GstObjectAPI.GstObjectStruct;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;
import org.freedesktop.gstreamer.lowlevel.annotations.IncRef;
import org.freedesktop.gstreamer.message.Message;
import org.freedesktop.gstreamer.query.Query;

public interface GstElementAPI extends com.sun.jna.Library {
  GstElementAPI GSTELEMENT_API = GstNative.load(GstElementAPI.class);

  GType gst_element_get_type();

  StateChangeReturn gst_element_set_state(Element elem, State state);

  StateChangeReturn gst_element_get_state(Element elem, State[] state, State[] pending, long timeout);

  boolean gst_element_set_locked_state(Element element, boolean locked_state);

  boolean gst_element_sync_state_with_parent(Element elem);

  boolean gst_element_query_position(Element elem, Format fmt, long[] pos);

  boolean gst_element_query_duration(Element elem, Format fmt, long[] pos);

  boolean gst_element_query(Element elem, Query query);

  boolean gst_element_seek(Element element, double rate, Format format, int flags, SeekType start_type, long start, SeekType stop_type, long stop);

  boolean gst_element_seek_simple(Element elem, Format format, int seek_flags, long seek_pos);

  boolean gst_element_link(Element elem1, Element elem2);

  boolean gst_element_link_filtered(Element elem1, Element elem2, Caps filter);

  boolean gst_element_link_many(Element... elements);

  void gst_element_unlink_many(Element... elements);

  void gst_element_unlink(Element elem1, Element elem2);

  @CallerOwnsReturn Pad gst_element_get_pad(Element elem, String name);

  @CallerOwnsReturn Pad gst_element_get_static_pad(Element element, String name);

  @CallerOwnsReturn Pad gst_element_get_request_pad(Element element, String name);

  void gst_element_release_request_pad(Element element, Pad pad);

  boolean gst_element_add_pad(Element elem, Pad pad);

  boolean gst_element_remove_pad(Element elem, @IncRef Pad pad);

  boolean gst_element_link_pads(Element src, String srcpadname, Element dest, String destpadname);

  void gst_element_unlink_pads(Element src, String srcpadname, Element dest, String destpadname);

  boolean gst_element_link_pads_filtered(Element src, String srcpadname, Element dest, String destpadname, Caps filter);

  GstIteratorPtr gst_element_iterate_pads(Element element);

  GstIteratorPtr gst_element_iterate_src_pads(Element element);

  GstIteratorPtr gst_element_iterate_sink_pads(Element element);

  ElementFactory gst_element_get_factory(Element element);

  @CallerOwnsReturn Bus gst_element_get_bus(Element element);

  boolean gst_element_send_event(Element element, @IncRef Event event);

  boolean gst_element_post_message(Element element, @IncRef Message message);

  boolean gst_element_implements_interface(Element element, NativeLong iface_type);

  Clock gst_element_get_clock(Element element);

  boolean gst_element_set_clock(Element element, Clock clock);

  void gst_element_set_base_time(Element element, long time);

  long gst_element_get_base_time(Element element);

  void gst_element_set_start_time(Element element, long time);

  long gst_element_get_start_time(Element element);

  void gst_element_set_context(Element element, GstContextPtr context);

  GList gst_element_get_contexts(Element element);

  @CallerOwnsReturn GstContextPtr gst_element_get_context(Element element, String context_type);

  @CallerOwnsReturn GstContextPtr gst_element_get_context_unlocked(Element element, String context_type);

  public static final class GstElementStruct extends com.sun.jna.Structure {
    public GstObjectStruct object;
    public volatile Pointer state_lock;
    public volatile Pointer state_cond;
    public volatile int state_cookie;
    public volatile State target_state;
    public volatile State current_state;
    public volatile State next_state;
    public volatile State pending_state;
    public volatile StateChangeReturn last_return;
    public volatile Pointer bus;
    public volatile Pointer clock;
    public volatile long base_time;
    public volatile long start_time;
    public volatile short numpads;
    public volatile Pointer pads;
    public volatile short numsrcpads;
    public volatile Pointer srcpads;
    public volatile short numsinkpads;
    public volatile Pointer sinkpads;
    public volatile int pads_cookie;
    public volatile Pointer contexts;
    public volatile Pointer[] _gst_reserved = new Pointer[GstAPI.GST_PADDING - 1];

    public GstElementStruct(Pointer handle) {
      super(handle);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"object", "state_lock", "state_cond", "state_cookie", "target_state", "current_state", "next_state", "pending_state", "last_return", "bus", "clock", "base_time", "start_time", "numpads", "pads", "numsrcpads", "srcpads", "numsinkpads", "sinkpads", "pads_cookie", "contexts", "_gst_reserved"});
    }
  }

  public static final class GstElementClass extends com.sun.jna.Structure {
    public static interface PadAdded extends GstCallback {
      public void callback(Element element, Pad pad);
    }

    public static interface PadRemoved extends GstCallback {
      public void callback(Element element, Pad pad);
    }

    public static interface NoMorePads extends GstCallback {
      public void callback(Element element);
    }

    public static interface RequestNewPad extends GstCallback {
      public Pad callback(Element element, Pointer templ, String name, Caps caps);
    }

    public static interface ReleasePad extends GstCallback {
      public void callback(Element element, Pad pad);
    }

    public static interface GetState extends GstCallback {
      public StateChangeReturn callback(Element element, Pointer state, Pointer pending, long timeout);
    }

    public static interface SetState extends GstCallback {
      public StateChangeReturn callback(Element element, State state);
    }

    public static interface ChangeState extends GstCallback {
      public StateChangeReturn callback(Element element, int transition);
    }

    public static interface StateChanged extends GstCallback {
      public StateChangeReturn callback(Element element, State oldState, State newState, State pending);
    }

    public static interface SetBus extends GstCallback {
      public void callback(Element element, Bus bus);
    }

    public static interface ProvideClock extends GstCallback {
      public void callback(Element element);
    }

    public static interface SetClock extends GstCallback {
      public void callback(Element element, Clock clock);
    }

    public static interface SendEvent extends GstCallback {
      boolean callback(Element element, Event event);
    }

    public static interface QueryNotify extends GstCallback {
      boolean callback(Element element, Query query);
    }

    public static interface PostMessage extends GstCallback {
      boolean callback(Element element, Message message);
    }

    public GstObjectClass parent_class;
    public volatile Pointer metadata;
    public volatile ElementFactory elementfactory;
    public volatile Pointer padtemplates;
    public volatile int numpadtemplates;
    public volatile int pad_templ_cookie;
    public PadAdded pad_added;
    public PadRemoved pad_removed;
    public NoMorePads no_more_pads;
    public RequestNewPad request_new_pad;
    public ReleasePad release_pad;
    public GetState get_state;
    public SetState set_state;
    public ChangeState change_state;
    public StateChanged state_changed;
    public volatile SetBus set_bus;
    public volatile ProvideClock provide_clock;
    public volatile SetClock set_clock;
    public volatile SendEvent send_event;
    public volatile QueryNotify query;
    public volatile PostMessage post_message;
    public volatile Pointer set_context;
    public volatile Pointer[] _gst_reserved = new Pointer[GstAPI.GST_PADDING_LARGE - 2];

    public GstElementClass(Pointer ptr) {
      super(ptr);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"parent_class", "metadata", "elementfactory", "padtemplates", "numpadtemplates", "pad_templ_cookie", "pad_added", "pad_removed", "no_more_pads", "request_new_pad", "release_pad", "get_state", "set_state", "change_state", "state_changed", "set_bus", "provide_clock", "set_clock", "send_event", "query", "post_message", "set_context", "_gst_reserved"});
    }
  }
}
