package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.lowlevel.GstContextAPI;
import org.freedesktop.gstreamer.lowlevel.GstContextPtr;

public class Context extends MiniObject {
  public static final String GTYPE_NAME = "GstContext";
  private final Handle handle;

  public Context(String contextType) {
    this(contextType, true);
  }

  public Context(String context_type, boolean persistent) {
    this(new Handle(GstContextAPI.GSTCONTEXT_API.gst_context_new(context_type, persistent), true), false);
  }

  Context(Handle handle, boolean needRef) {
    super(handle, needRef);
    this.handle = handle;
  }

  Context(Initializer init) {
    this(new Handle(init.ptr.as(GstContextPtr.class, GstContextPtr::new), init.ownsHandle), init.needRef);
  }

  public Structure getStructure() {
    return GstContextAPI.GSTCONTEXT_API.gst_context_get_structure(handle.getPointer());
  }

  public Structure getWritableStructure() {
    return GstContextAPI.GSTCONTEXT_API.gst_context_writable_structure(handle.getPointer());
  }

  public String getContextType() {
    return GstContextAPI.GSTCONTEXT_API.gst_context_get_context_type(handle.getPointer());
  }

  protected static class Handle extends MiniObject.Handle {
    public Handle(GstContextPtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected GstContextPtr getPointer() {
      return (GstContextPtr) super.getPointer();
    }
  }
}
