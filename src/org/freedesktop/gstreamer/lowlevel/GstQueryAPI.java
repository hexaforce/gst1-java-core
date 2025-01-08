package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.BufferPool;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.glib.GQuark;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;
import org.freedesktop.gstreamer.query.Query;
import org.freedesktop.gstreamer.query.QueryType;

public interface GstQueryAPI extends com.sun.jna.Library {
  GstQueryAPI GSTQUERY_API = GstNative.load(GstQueryAPI.class);

  String gst_query_type_get_name(QueryType query);

  GQuark gst_query_type_to_quark(QueryType query);

  @CallerOwnsReturn Query gst_query_new_position(Format format);

  @CallerOwnsReturn Pointer ptr_gst_query_new_position(Format format);

  void gst_query_set_position(Query query, Format format, long cur);

  void gst_query_parse_position(Query query, Format[] format, long[] cur);

  @CallerOwnsReturn Query gst_query_new_duration(Format format);

  @CallerOwnsReturn Pointer ptr_gst_query_new_duration(Format format);

  void gst_query_set_duration(Query query, Format format, long duration);

  void gst_query_parse_duration(Query query, Format[] format, long[] duration);

  @CallerOwnsReturn Query gst_query_new_latency();

  @CallerOwnsReturn Pointer ptr_gst_query_new_latency();

  void gst_query_set_latency(Query query, boolean live, long min_latency, long max_latency);

  void gst_query_parse_latency(Query query, boolean[] live, long[] min_latency, long[] max_latency);

  @CallerOwnsReturn Query gst_query_new_convert(Format src_format, long value, Format dest_format);

  @CallerOwnsReturn Pointer ptr_gst_query_new_convert(Format src_format, long value, Format dest_format);

  void gst_query_set_convert(Query query, Format src_format, long src_value, Format dest_format, long dest_value);

  void gst_query_parse_convert(Query query, Format[] src_format, long[] src_value, Format[] dest_format, long[] dest_value);

  @CallerOwnsReturn Query gst_query_new_segment(Format format);

  @CallerOwnsReturn Pointer ptr_gst_query_new_segment(Format format);

  void gst_query_set_segment(Query query, double rate, Format format, long start_value, long stop_value);

  void gst_query_parse_segment(Query query, double[] rate, Format[] format, long[] start_value, long[] stop_value);

  Structure gst_query_get_structure(Query query);

  @CallerOwnsReturn Query gst_query_new_seeking(Format format);

  @CallerOwnsReturn Pointer ptr_gst_query_new_seeking(Format format);

  void gst_query_set_seeking(Query query, Format format, boolean seekable, long segment_start, long segment_end);

  void gst_query_parse_seeking(Query query, Format[] format, boolean[] seekable, long[] segment_start, long[] segment_end);

  @CallerOwnsReturn Query gst_query_new_formats();

  Pointer ptr_gst_query_new_formats();

  void gst_query_set_formats(Query query, int n_formats, Format... formats);

  void gst_query_set_formatsv(Query query, int n_formats, Format[] formats);

  void gst_query_parse_n_formats(Query query, int[] n_formats);

  void gst_query_parse_nth_format(Query query, int nth, Format[] format);

  @CallerOwnsReturn Query gst_query_new_allocation(Caps caps, boolean need_pool);

  Pointer ptr_gst_query_new_allocation(Caps caps, boolean need_pool);

  void gst_query_parse_allocation(Query query, Pointer[] caps, boolean[] need_pool);

  void gst_query_add_allocation_meta(Query query, GType api, Structure params);

  void gst_query_add_allocation_pool(Query query, BufferPool pool, int size, int min_buffers, int max_buffers);

  int gst_query_get_n_allocation_pools(Query query);

  public static final class QueryStruct extends com.sun.jna.Structure {
    public volatile GstMiniObjectAPI.MiniObjectStruct mini_object;
    public volatile QueryType type;

    public QueryStruct(Pointer ptr) {
      useMemory(ptr);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"mini_object", "type"});
    }
  }

  public interface GstQueryTypeFlags {
    public static final int UPSTREAM = 1;
    public static final int DOWNSTREAM = 1 << 1;
    public static final int SERIALIZED = 1 << 2;
    public static final int BOTH = UPSTREAM | DOWNSTREAM;
  }

  public static final int GST_QUERY_NUM_SHIFT = 8;
}
