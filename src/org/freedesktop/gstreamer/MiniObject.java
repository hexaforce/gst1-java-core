package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstMiniObjectAPI.GSTMINIOBJECT_API;

import org.freedesktop.gstreamer.glib.RefCountedObject;
import org.freedesktop.gstreamer.lowlevel.GPointer;
import org.freedesktop.gstreamer.lowlevel.GstMiniObjectAPI.MiniObjectStruct;
import org.freedesktop.gstreamer.lowlevel.GstMiniObjectPtr;

@SuppressWarnings("unchecked")
public abstract class MiniObject extends RefCountedObject {
  protected MiniObject(Initializer init) {
    this(new Handle(init.ptr.as(GstMiniObjectPtr.class, GstMiniObjectPtr::new), init.ownsHandle), init.needRef);
  }

  protected MiniObject(Handle handle, boolean needRef) {
    super(handle, needRef);
  }

  public boolean isWritable() {
    return GSTMINIOBJECT_API.gst_mini_object_is_writable(this);
  }

  protected <T extends MiniObject> T makeWritable() {
    MiniObject result = GSTMINIOBJECT_API.gst_mini_object_make_writable(this);
    if (result == null) {
      throw new NullPointerException("Could not make " + this.getClass().getSimpleName() + " writable");
    }
    return (T) result;
  }

  public <T extends MiniObject> T copy() {
    MiniObject result = GSTMINIOBJECT_API.gst_mini_object_copy(this);
    if (result == null) {
      throw new NullPointerException("Could not make a copy of " + this.getClass().getSimpleName());
    }
    return (T) result;
  }

  public int getRefCount() {
    final MiniObjectStruct struct = new MiniObjectStruct(getRawPointer());
    return (Integer) struct.readField("refcount");
  }

  protected static class Handle extends RefCountedObject.Handle {
    public Handle(GstMiniObjectPtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {
      GSTMINIOBJECT_API.gst_mini_object_unref(ptr.as(GstMiniObjectPtr.class, GstMiniObjectPtr::new));
    }

    @Override
    protected void ref() {
      GSTMINIOBJECT_API.gst_mini_object_ref(getPointer());
    }

    @Override
    protected void unref() {
      GSTMINIOBJECT_API.gst_mini_object_unref(getPointer());
    }

    @Override
    protected GstMiniObjectPtr getPointer() {
      return (GstMiniObjectPtr) super.getPointer();
    }
  }
}
