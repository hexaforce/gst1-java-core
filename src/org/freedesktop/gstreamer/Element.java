package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GObjectAPI.GOBJECT_API;
import static org.freedesktop.gstreamer.lowlevel.GstElementAPI.GSTELEMENT_API;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.freedesktop.gstreamer.event.Event;
import org.freedesktop.gstreamer.event.SeekFlags;
import org.freedesktop.gstreamer.event.SeekType;
import org.freedesktop.gstreamer.glib.NativeFlags;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;
import org.freedesktop.gstreamer.lowlevel.GstContextPtr;
import org.freedesktop.gstreamer.lowlevel.GstIteratorPtr;
import org.freedesktop.gstreamer.lowlevel.GstObjectPtr;
import org.freedesktop.gstreamer.message.Message;
import org.freedesktop.gstreamer.query.Query;

public class Element extends GstObject {
  public static final String GTYPE_NAME = "GstElement";

  protected Element(Initializer init) {
    super(init);
  }

  Element(Handle handle, boolean needRef) {
    super(handle, needRef);
  }

  protected static Initializer makeRawElement(String factoryName, String elementName) {
    return Natives.initializer(ElementFactory.makeRawElement(factoryName, elementName));
  }

  public boolean link(Element dest) {
    return GSTELEMENT_API.gst_element_link(this, dest);
  }

  public boolean linkFiltered(Element dest, Caps filter) {
    return GSTELEMENT_API.gst_element_link_filtered(this, dest, filter);
  }

  public void unlink(Element dest) {
    GSTELEMENT_API.gst_element_unlink(this, dest);
  }

  public boolean isPlaying() {
    return getState() == State.PLAYING;
  }

  public StateChangeReturn play() {
    return setState(State.PLAYING);
  }

  public StateChangeReturn ready() {
    return setState(State.READY);
  }

  public StateChangeReturn pause() {
    return setState(State.PAUSED);
  }

  public StateChangeReturn stop() {
    return setState(State.NULL);
  }

  public StateChangeReturn setState(State state) {
    return GSTELEMENT_API.gst_element_set_state(this, state);
  }

  public boolean setLockedState(boolean locked_state) {
    return GSTELEMENT_API.gst_element_set_locked_state(this, locked_state);
  }

  public State getState() {
    return getState(-1);
  }

  public State getState(long timeout, TimeUnit units) {
    State[] state = new State[1];
    GSTELEMENT_API.gst_element_get_state(this, state, null, units.toNanos(timeout));
    return state[0];
  }

  public State getState(long timeout) {
    State[] state = new State[1];
    GSTELEMENT_API.gst_element_get_state(this, state, null, timeout);
    return state[0];
  }

  public void getState(long timeout, State[] states) {
    State[] state = new State[1];
    State[] pending = new State[1];
    GSTELEMENT_API.gst_element_get_state(this, state, pending, timeout);
    states[0] = state[0];
    states[1] = pending[0];
  }

  public boolean syncStateWithParent() {
    return GSTELEMENT_API.gst_element_sync_state_with_parent(this);
  }

  public void setCaps(Caps caps) {
    GOBJECT_API.g_object_set(this, "caps", caps);
  }

  public Pad getStaticPad(String padname) {
    return GSTELEMENT_API.gst_element_get_static_pad(this, padname);
  }

  public List<Pad> getPads() {
    return padList(GSTELEMENT_API.gst_element_iterate_pads(this));
  }

  public List<Pad> getSrcPads() {
    return padList(GSTELEMENT_API.gst_element_iterate_src_pads(this));
  }

  public List<Pad> getSinkPads() {
    return padList(GSTELEMENT_API.gst_element_iterate_sink_pads(this));
  }

  private List<Pad> padList(GstIteratorPtr iter) {
    return GstIterator.asList(iter, Pad.class);
  }

  public boolean addPad(Pad pad) {
    return GSTELEMENT_API.gst_element_add_pad(this, pad);
  }

  public Pad getRequestPad(String name) {
    return GSTELEMENT_API.gst_element_get_request_pad(this, name);
  }

  public void releaseRequestPad(Pad pad) {
    GSTELEMENT_API.gst_element_release_request_pad(this, pad);
  }

  public boolean removePad(Pad pad) {
    return GSTELEMENT_API.gst_element_remove_pad(this, pad);
  }

  public ElementFactory getFactory() {
    return GSTELEMENT_API.gst_element_get_factory(this);
  }

  public Bus getBus() {
    return GSTELEMENT_API.gst_element_get_bus(this);
  }

  public boolean sendEvent(Event ev) {
    return GSTELEMENT_API.gst_element_send_event(this, ev);
  }

  public static interface PAD_ADDED {
    public void padAdded(Element element, Pad pad);
  }

  public static interface PAD_REMOVED {
    public void padRemoved(Element element, Pad pad);
  }

  public static interface NO_MORE_PADS {
    public void noMorePads(Element element);
  }

  public void connect(final PAD_ADDED listener) {
    connect(PAD_ADDED.class, listener, new GstCallback() {
      @SuppressWarnings("unused")
      public void callback(Element elem, Pad pad) {
        listener.padAdded(elem, pad);
      }
    });
  }

  public void disconnect(PAD_ADDED listener) {
    disconnect(PAD_ADDED.class, listener);
  }

  public void connect(final PAD_REMOVED listener) {
    connect(PAD_REMOVED.class, listener, new GstCallback() {
      @SuppressWarnings("unused")
      public void callback(Element elem, Pad pad) {
        listener.padRemoved(elem, pad);
      }
    });
  }

  public void disconnect(PAD_REMOVED listener) {
    disconnect(PAD_REMOVED.class, listener);
  }

  public void connect(final NO_MORE_PADS listener) {
    connect(NO_MORE_PADS.class, listener, new GstCallback() {
      @SuppressWarnings("unused")
      public void callback(Element elem) {
        listener.noMorePads(elem);
      }
    });
  }

  public void disconnect(NO_MORE_PADS listener) {
    disconnect(NO_MORE_PADS.class, listener);
  }

  public static boolean linkMany(Element... elements) {
    return GSTELEMENT_API.gst_element_link_many(elements);
  }

  public static void unlinkMany(Element... elements) {
    GSTELEMENT_API.gst_element_unlink_many(elements);
  }

  public static boolean linkPads(Element src, String srcPadName, Element dest, String destPadName) {
    return GSTELEMENT_API.gst_element_link_pads(src, srcPadName, dest, destPadName);
  }

  public static boolean linkPadsFiltered(Element src, String srcPadName, Element dest, String destPadName, Caps caps) {
    return GSTELEMENT_API.gst_element_link_pads_filtered(src, srcPadName, dest, destPadName, caps);
  }

  public static void unlinkPads(Element src, String srcPadName, Element dest, String destPadName) {
    GSTELEMENT_API.gst_element_unlink_pads(src, srcPadName, dest, destPadName);
  }

  public boolean postMessage(Message message) {
    return GSTELEMENT_API.gst_element_post_message(this, message);
  }

  public Clock getClock() {
    return GSTELEMENT_API.gst_element_get_clock(this);
  }

  public long getBaseTime() {
    return GSTELEMENT_API.gst_element_get_base_time(this);
  }

  public void setBaseTime(long time) {
    GSTELEMENT_API.gst_element_set_base_time(this, time);
  }

  public long getStartTime() {
    return GSTELEMENT_API.gst_element_get_start_time(this);
  }

  public void setStartTime(long time) {
    GSTELEMENT_API.gst_element_set_start_time(this, time);
  }

  public boolean query(Query query) {
    return GSTELEMENT_API.gst_element_query(this, query);
  }

  public void setContext(Context context) {
    GstContextPtr gstContextPtr = Natives.getPointer(context).as(GstContextPtr.class, GstContextPtr::new);
    GSTELEMENT_API.gst_element_set_context(this, gstContextPtr);
  }

  public Context getContext(String context_type) {
    GstContextPtr gstContextPtr = GSTELEMENT_API.gst_element_get_context(this, context_type);
    return gstContextPtr != null ? Natives.callerOwnsReturn(gstContextPtr, Context.class) : null;
  }

  public long queryDuration(Format format) {
    long[] dur = {0};
    return GSTELEMENT_API.gst_element_query_duration(this, format, dur) ? dur[0] : -1L;
  }

  public long queryPosition(Format format) {
    long[] pos = {0};
    return GSTELEMENT_API.gst_element_query_position(this, format, pos) ? pos[0] : -1L;
  }

  public boolean seek(double rate, Format format, Set<SeekFlags> seekFlags, SeekType startType, long start, SeekType stopType, long stop) {
    return GSTELEMENT_API.gst_element_seek(this, rate, format, NativeFlags.toInt(seekFlags), startType, start, stopType, stop);
  }

  public boolean seekSimple(Format format, Set<SeekFlags> seekFlags, long seekPosition) {
    return GSTELEMENT_API.gst_element_seek_simple(this, format, NativeFlags.toInt(seekFlags), seekPosition);
  }

  static class Handle extends GstObject.Handle {
    public Handle(GstObjectPtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }
  }
}
