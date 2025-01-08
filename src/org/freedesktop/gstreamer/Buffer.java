package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstBufferAPI.GSTBUFFER_API;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.freedesktop.gstreamer.glib.NativeFlags;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.freedesktop.gstreamer.lowlevel.GstBufferAPI;
import org.freedesktop.gstreamer.lowlevel.GstBufferAPI.BufferStruct;
import org.freedesktop.gstreamer.lowlevel.GstBufferAPI.MapInfoStruct;
import org.freedesktop.gstreamer.lowlevel.GstMetaPtr;

public class Buffer extends MiniObject {
  public static final String GTYPE_NAME = "GstBuffer";
  private final MapInfoStruct mapInfo;
  private final BufferStruct struct;

  public Buffer() {
    this(Natives.initializer(GSTBUFFER_API.ptr_gst_buffer_new()));
  }

  public Buffer(int size) {
    this(Natives.initializer(allocBuffer(size)));
  }

  Buffer(Initializer init) {
    super(init);
    mapInfo = new MapInfoStruct();
    struct = new BufferStruct(getRawPointer());
  }

  private static Pointer allocBuffer(int size) {
    Pointer ptr = GSTBUFFER_API.ptr_gst_buffer_new_allocate(null, size, null);
    if (ptr == null) {
      throw new OutOfMemoryError("Could not allocate Buffer of size " + size);
    }
    return ptr;
  }

  public ByteBuffer map(boolean writable) {
    final boolean ok = GSTBUFFER_API.gst_buffer_map(this, mapInfo, writable ? GstBufferAPI.GST_MAP_WRITE : GstBufferAPI.GST_MAP_READ);
    if (ok && mapInfo.data != null) {
      return mapInfo.data.getByteBuffer(0, mapInfo.size.intValue());
    }
    return null;
  }

  public void unmap() {
    GSTBUFFER_API.gst_buffer_unmap(this, mapInfo);
  }

  public int getMemoryCount() {
    return GSTBUFFER_API.gst_buffer_n_memory(this);
  }

  public long getDecodeTimestamp() {
    return (long) this.struct.readField("dts");
  }

  public void setDecodeTimestamp(long val) {
    this.struct.writeField("dts", val);
  }

  public long getPresentationTimestamp() {
    return (long) this.struct.readField("pts");
  }

  public void setPresentationTimestamp(long val) {
    this.struct.writeField("pts", val);
  }

  public long getDuration() {
    return (long) this.struct.readField("duration");
  }

  public void setDuration(long val) {
    this.struct.writeField("duration", val);
  }

  public long getOffset() {
    return (Long) this.struct.readField("offset");
  }

  public void setOffset(long val) {
    this.struct.writeField("offset", val);
  }

  public long getOffsetEnd() {
    return (Long) this.struct.readField("offset_end");
  }

  public void setOffsetEnd(long val) {
    this.struct.writeField("offset_end", val);
  }

  @Gst.Since(minor = 10)
  public EnumSet<BufferFlags> getFlags() {
    Gst.checkVersion(1, 10);
    int nativeInt = GstBufferAPI.GSTBUFFER_API.gst_buffer_get_flags(this);
    return NativeFlags.fromInt(BufferFlags.class, nativeInt);
  }

  public <T extends Meta> T getMeta(Meta.API<T> api) {
    GType apiType = api.getAPIGType();
    if (apiType == GType.INVALID) {
      return null;
    }
    GstMetaPtr ptr = GSTBUFFER_API.gst_buffer_get_meta(this, apiType);
    if (ptr == null) {
      return null;
    }
    return Natives.objectFor(ptr, api.getImplClass(), false, false);
  }

  public Iterator<Meta> iterateMeta() {
    return new MetaIterator(this);
  }

  @Gst.Since(minor = 14)
  public <T extends Meta> boolean hasMeta(Meta.API<T> api) {
    return getMetaCount(api) > 0;
  }

  @Gst.Since(minor = 14)
  public <T extends Meta> int getMetaCount(Meta.API<T> api) {
    Gst.checkVersion(1, 14);
    GType apiType = api.getAPIGType();
    if (apiType == GType.INVALID) {
      return 0;
    }
    return GSTBUFFER_API.gst_buffer_get_n_meta(this, apiType);
  }

  @Gst.Since(minor = 10)
  public boolean setFlags(EnumSet<BufferFlags> flags) {
    Gst.checkVersion(1, 10);
    return GstBufferAPI.GSTBUFFER_API.gst_buffer_set_flags(this, NativeFlags.toInt(flags));
  }

  @Gst.Since(minor = 10)
  public boolean unsetFlags(EnumSet<BufferFlags> flags) {
    Gst.checkVersion(1, 10);
    return GstBufferAPI.GSTBUFFER_API.gst_buffer_unset_flags(this, NativeFlags.toInt(flags));
  }

  private static class MetaIterator implements Iterator<Meta> {
    private final PointerByReference state;
    private final Buffer buffer;
    private Meta next;

    MetaIterator(final Buffer buffer) {
      state = new PointerByReference();
      this.buffer = buffer;
    }

    @Override
    public boolean hasNext() {
      if (next == null) {
        next = getNext();
      }
      return next != null;
    }

    @Override
    public Meta next() {
      if (!hasNext() || next == null) {
        throw new NoSuchElementException();
      }
      Meta m = next;
      next = null;
      return m;
    }

    private Meta getNext() {
      return Natives.objectFor(GSTBUFFER_API.gst_buffer_iterate_meta(this.buffer, this.state), Meta.class, false, false);
    }
  }
}
