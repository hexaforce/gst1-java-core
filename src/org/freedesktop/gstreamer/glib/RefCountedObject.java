package org.freedesktop.gstreamer.glib;

import org.freedesktop.gstreamer.lowlevel.GPointer;

public abstract class RefCountedObject extends NativeObject {
  protected RefCountedObject(Handle handle) {
    super(handle);
  }

  protected RefCountedObject(Handle handle, boolean needRef) {
    super(handle);
    if (needRef) {
      handle.ref();
    }
  }

  protected abstract static class Handle extends NativeObject.Handle {
    public Handle(GPointer ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    protected abstract void ref();

    protected abstract void unref();
  }
}
