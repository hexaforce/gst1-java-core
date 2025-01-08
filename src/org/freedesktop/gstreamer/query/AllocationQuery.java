package org.freedesktop.gstreamer.query;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.BufferPool;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.freedesktop.gstreamer.lowlevel.GstQueryAPI;

public class AllocationQuery extends Query {
  AllocationQuery(Initializer init) {
    super(init);
  }

  public AllocationQuery(Caps caps, boolean need_pool) {
    this(Natives.initializer(GstQueryAPI.GSTQUERY_API.ptr_gst_query_new_allocation(caps, need_pool)));
  }

  public boolean isPoolNeeded() {
    boolean[] need_pool = {false};
    GstQueryAPI.GSTQUERY_API.gst_query_parse_allocation(this, null, need_pool);
    return need_pool[0];
  }

  public Caps getCaps() {
    Pointer[] ptr = new Pointer[1];
    GstQueryAPI.GSTQUERY_API.gst_query_parse_allocation(this, ptr, null);
    return Natives.objectFor(ptr[0], Caps.class, false, true);
  }

  void addAllocationMeta(GType api, Structure params) {
    GstQueryAPI.GSTQUERY_API.gst_query_add_allocation_meta(this, api, params);
  }

  public void addAllocationPool(BufferPool pool, int size, int min_buffers, int max_buffers) {
    GstQueryAPI.GSTQUERY_API.gst_query_add_allocation_pool(this, pool, size, min_buffers, max_buffers);
  }
}
