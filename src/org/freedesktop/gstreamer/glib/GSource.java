package org.freedesktop.gstreamer.glib;

import static org.freedesktop.gstreamer.lowlevel.GlibAPI.GLIB_API;

import com.sun.jna.Pointer;
import java.util.concurrent.Callable;
import org.freedesktop.gstreamer.lowlevel.GPointer;
import org.freedesktop.gstreamer.lowlevel.GlibAPI;

public class GSource extends RefCountedObject {
  GSource(Initializer init) {
    super(new Handle(init.ptr, init.ownsHandle), init.needRef);
  }

  public int attach(GMainContext context) {
    return GLIB_API.g_source_attach(this, context);
  }

  public void setCallback(final Callable<Boolean> call) {
    this.callback = new GlibAPI.GSourceFunc() {
      public boolean callback(Pointer data) {
        if (GLIB_API.g_source_is_destroyed(getRawPointer())) {
          return false;
        }
        try {
          return call.call().booleanValue();
        } catch (Exception ex) {
          return false;
        }
      }
    };
    GLIB_API.g_source_set_callback(this, callback, null, null);
  }

  private GlibAPI.GSourceFunc callback;

  private static final class Handle extends RefCountedObject.Handle {
    Handle(GPointer ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {
      GLIB_API.g_source_destroy(ptr.getPointer());
      GLIB_API.g_source_unref(ptr.getPointer());
    }

    @Override
    protected void ref() {
      GLIB_API.g_source_ref(getPointer().getPointer());
    }

    @Override
    protected void unref() {
      GLIB_API.g_source_unref(getPointer().getPointer());
    }
  }
}
