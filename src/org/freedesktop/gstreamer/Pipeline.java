package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstElementAPI.GSTELEMENT_API;
import static org.freedesktop.gstreamer.lowlevel.GstPipelineAPI.GSTPIPELINE_API;
import static org.freedesktop.gstreamer.lowlevel.GstQueryAPI.GSTQUERY_API;

import com.sun.jna.Pointer;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import org.freedesktop.gstreamer.event.SeekFlags;
import org.freedesktop.gstreamer.event.SeekType;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstObjectPtr;
import org.freedesktop.gstreamer.query.Query;

public class Pipeline extends Bin {
  public static final String GST_NAME = "pipeline";
  public static final String GTYPE_NAME = "GstPipeline";
  private static Logger LOG = Logger.getLogger(Pipeline.class.getName());
  private final Handle handle;

  protected Pipeline(Initializer init) {
    this(new Handle(init.ptr.as(GstObjectPtr.class, GstObjectPtr::new), init.ownsHandle), init.needRef);
  }

  Pipeline(Handle handle, boolean needRef) {
    super(handle, needRef);
    this.handle = handle;
    handle.busRef.set(GSTPIPELINE_API.gst_pipeline_get_bus(this));
  }

  public Pipeline() {
    this(Natives.initializer(GSTPIPELINE_API.ptr_gst_pipeline_new(null), false));
  }

  public Pipeline(String name) {
    this(initializer(name));
  }

  private static Initializer initializer(String name) {
    Pointer new_pipeline = GSTPIPELINE_API.ptr_gst_pipeline_new(name);
    return Natives.initializer(new_pipeline, false);
  }

  public void setAutoFlushBus(boolean flush) {
    GSTPIPELINE_API.gst_pipeline_set_auto_flush_bus(this, flush);
  }

  public boolean getAutoFlushBus() {
    return GSTPIPELINE_API.gst_pipeline_get_auto_flush_bus(this);
  }

  public boolean setClock(Clock clock) {
    return GSTPIPELINE_API.gst_pipeline_set_clock(this, clock);
  }

  @Override
  public Clock getClock() {
    return GSTPIPELINE_API.gst_pipeline_get_clock(this);
  }

  public void useClock(Clock clock) {
    GSTPIPELINE_API.gst_pipeline_use_clock(this, clock);
  }

  @Override
  public Bus getBus() {
    return handle.busRef.get();
  }

  public boolean seek(long time) {
    return seek(time, TimeUnit.NANOSECONDS);
  }

  public boolean seek(long time, TimeUnit unit) {
    return seek(1.0, Format.TIME, EnumSet.of(SeekFlags.FLUSH, SeekFlags.KEY_UNIT), SeekType.SET, TimeUnit.NANOSECONDS.convert(time, unit), SeekType.NONE, -1);
  }

  public boolean seek(double rate, Format format, EnumSet<SeekFlags> seekFlags, SeekType startType, long start, SeekType stopType, long stop) {
    return super.seek(rate, format, seekFlags, startType, start, stopType, stop);
  }

  @Override
  public boolean seek(double rate, Format format, Set<SeekFlags> seekFlags, SeekType startType, long start, SeekType stopType, long stop) {
    return super.seek(rate, format, seekFlags, startType, start, stopType, stop);
  }

  public long queryPosition(TimeUnit unit) {
    return unit.convert(queryPosition(Format.TIME), TimeUnit.NANOSECONDS);
  }

  @Override
  public long queryPosition(Format format) {
    return super.queryPosition(format);
  }

  public long queryDuration(TimeUnit unit) {
    return unit.convert(queryDuration(Format.TIME), TimeUnit.NANOSECONDS);
  }

  @Override
  public long queryDuration(Format format) {
    return super.queryDuration(format);
  }

  public Segment querySegment() {
    return querySegment(Format.TIME);
  }

  public Segment querySegment(Format format) {
    Query qry = GSTQUERY_API.gst_query_new_segment(format);
    GSTELEMENT_API.gst_element_query(this, qry);
    double[] rate = {0.0D};
    Format[] fmt = {Format.UNDEFINED};
    long[] start_value = {0};
    long[] stop_value = {0};
    GSTQUERY_API.gst_query_parse_segment(qry, rate, fmt, start_value, stop_value);
    return new Segment(rate[0], fmt[0], start_value[0], stop_value[0]);
  }

  static class Handle extends Bin.Handle {
    private final AtomicReference<Bus> busRef;

    public Handle(GstObjectPtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
      this.busRef = new AtomicReference<>();
    }

    @Override
    public void invalidate() {
      disposeBus();
      super.invalidate();
    }

    @Override
    public void dispose() {
      disposeBus();
      super.dispose();
    }

    private void disposeBus() {
      Bus bus = busRef.getAndSet(null);
      if (bus != null) {
        bus.dispose();
      }
    }
  }
}
