package org.freedesktop.gstreamer.glib;

import static org.freedesktop.gstreamer.lowlevel.GlibAPI.GLIB_API;

import org.freedesktop.gstreamer.lowlevel.GPointer;

public class GMainContext extends RefCountedObject {
  public GMainContext() {
    this(Natives.initializer(GLIB_API.g_main_context_new()));
  }

  private GMainContext(Initializer init) {
    super(new Handle(init.ptr, init.ownsHandle), init.needRef);
  }

  public int attach(GSource source) {
    return GLIB_API.g_source_attach(source, this);
  }

  public static GMainContext getDefaultContext() {
    return new GMainContext(Natives.initializer(GLIB_API.g_main_context_default(), false, false));
  }

  private static final class Handle extends RefCountedObject.Handle {
    public Handle(GPointer ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {
      GLIB_API.g_main_context_unref(ptr);
    }

    @Override
    protected void ref() {
      GLIB_API.g_main_context_ref(getPointer());
    }

    @Override
    protected void unref() {
      GLIB_API.g_main_context_unref(getPointer());
    }
  }
}
