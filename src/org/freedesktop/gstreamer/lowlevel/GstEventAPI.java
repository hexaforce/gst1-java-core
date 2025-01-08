package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.TagList;
import org.freedesktop.gstreamer.event.Event;
import org.freedesktop.gstreamer.event.EventType;
import org.freedesktop.gstreamer.event.QOSType;
import org.freedesktop.gstreamer.event.SeekType;
import org.freedesktop.gstreamer.lowlevel.GstMiniObjectAPI.MiniObjectStruct;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;
import org.freedesktop.gstreamer.lowlevel.annotations.Invalidate;

public interface GstEventAPI extends com.sun.jna.Library {
  GstEventAPI GSTEVENT_API = GstNative.load(GstEventAPI.class);

  String gst_event_type_get_name(EventType type);

  int gst_event_type_get_flags(EventType type);

  GType gst_event_get_type();

  Structure gst_event_get_structure(Event event);

  Event gst_event_new_custom(EventType type, @Invalidate Structure structure);

  @CallerOwnsReturn Event gst_event_new_flush_start();

  Pointer ptr_gst_event_new_flush_start();

  @CallerOwnsReturn Event gst_event_new_flush_stop();

  Pointer ptr_gst_event_new_flush_stop();

  @CallerOwnsReturn Event gst_event_new_eos();

  Pointer ptr_gst_event_new_eos();

  @CallerOwnsReturn Event gst_event_new_segment(GstAPI.GstSegmentStruct segmentStruct);

  Pointer ptr_gst_event_new_segment(GstAPI.GstSegmentStruct segment);

  void gst_event_parse_segment(Event event, Pointer[] pointer);

  @CallerOwnsReturn Event gst_event_new_tag(@Invalidate TagList taglist);

  Pointer ptr_gst_event_new_tag(@Invalidate TagList taglist);

  void gst_event_parse_tag(Event event, PointerByReference taglist);

  void gst_event_parse_tag(Event event, Pointer[] taglist);

  @CallerOwnsReturn Event gst_event_new_buffer_size(Format format, long minsize, long maxsize, boolean async);

  Pointer ptr_gst_event_new_buffer_size(Format format, long minsize, long maxsize, boolean async);

  void gst_event_parse_buffer_size(Event event, Format[] format, long[] minsize, long[] maxsize, boolean[] async);

  @CallerOwnsReturn Event gst_event_new_qos(QOSType type, double proportion, long diff, long timestamp);

  Pointer ptr_gst_event_new_qos(QOSType type, double proportion, long diff, long timestamp);

  void gst_event_parse_qos(Event event, QOSType[] type, double[] proportion, long[] diff, long[] timestamp);

  @CallerOwnsReturn Event gst_event_new_seek(double rate, Format format, int flags, SeekType start_type, long start, SeekType stop_type, long stop);

  Pointer ptr_gst_event_new_seek(double rate, Format format, int flags, SeekType start_type, long start, SeekType stop_type, long stop);

  void gst_event_parse_seek(Event event, double[] rate, Format[] format, int[] flags, SeekType[] start_type, long[] start, SeekType[] stop_type, long[] stop);

  @CallerOwnsReturn Event gst_event_new_navigation(@Invalidate Structure structure);

  Pointer ptr_gst_event_new_navigation(@Invalidate Structure structure);

  @CallerOwnsReturn Event gst_event_new_latency(long latency);

  Pointer ptr_gst_event_new_latency(long latency);

  void gst_event_parse_latency(Event event, long[] latency);

  @CallerOwnsReturn Event gst_event_new_step(Format format, long amount, double rate, boolean flush, boolean intermediate);

  Pointer ptr_gst_event_new_step(Format format, long amount, double rate, boolean flush, boolean intermediate);

  @CallerOwnsReturn Event gst_event_new_caps(Caps caps);

  Pointer ptr_gst_event_new_caps(Caps caps);

  @CallerOwnsReturn Event gst_event_new_reconfigure();

  Pointer ptr_gst_event_new_reconfigure();

  @CallerOwnsReturn Event gst_event_new_stream_start(final String stream_id);

  Pointer ptr_gst_event_new_stream_start(final String stream_id);

  public static final class EventStruct extends com.sun.jna.Structure {
    public volatile MiniObjectStruct mini_object;
    public volatile EventType type;
    public volatile long timestamp;
    public volatile int seqnum;

    public EventStruct(Pointer ptr) {
      useMemory(ptr);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"mini_object", "type", "timestamp", "seqnum"});
    }
  }
}
