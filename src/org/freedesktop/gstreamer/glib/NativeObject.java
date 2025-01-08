/*
 * This file is part of gstreamer-java.
 * 
 * This code is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License version 3 only, as published by the Free Software Foundation.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License version 3 for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3 along with this work.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.freedesktop.gstreamer.glib;

import com.sun.jna.Pointer;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.lowlevel.GPointer;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.freedesktop.gstreamer.lowlevel.GTypedPtr;
import org.freedesktop.gstreamer.lowlevel.GstTypes;

public abstract class NativeObject implements AutoCloseable {
  private static final Level LIFECYCLE = Level.FINE;
  private static final Logger LOG = Logger.getLogger(NativeObject.class.getName());
  private static final ConcurrentMap<Pointer, NativeRef> INSTANCES = new ConcurrentHashMap<>();
  final Handle handle;
  private final Pointer ptr;

  protected NativeObject(Handle handle) {
    this.handle = Objects.requireNonNull(handle);
    this.ptr = handle.ptrRef.get().getPointer();
    if (handle.isCacheable()) {
      INSTANCES.put(this.ptr, new NativeRef(this, handle));
    }
  }

  public void disown() {
    LOG.log(LIFECYCLE, "Disowning " + getRawPointer());
    handle.ownsReference.set(false);
  }

  @Override
  public void close() {
    dispose();
  }

  public void dispose() {
    LOG.log(LIFECYCLE, "Disposing object " + getClass().getName() + " = " + handle);
    handle.dispose();
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof NativeObject && ((NativeObject) o).ptr.equals(ptr);
  }

  protected GPointer getPointer() {
    GPointer ptr = handle.ptrRef.get();
    if (ptr == null) {
      throw new IllegalStateException("Native object has been disposed");
    }
    return ptr;
  }

  protected Pointer getRawPointer() {
    GPointer ptr = handle.ptrRef.get();
    if (ptr == null) {
      throw new IllegalStateException("Native object has been disposed");
    }
    return ptr.getPointer();
  }

  @Override
  public int hashCode() {
    return ptr.hashCode();
  }

  public void invalidate() {
    LOG.log(LIFECYCLE, () -> "Invalidating object " + this + " = " + getRawPointer());
    handle.invalidate();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" + getRawPointer() + ")";
  }

  static <T extends NativeObject> T objectFor(GPointer gptr, Class<T> cls, int refAdjust, boolean ownsHandle) {
    if (gptr == null) {
      return null;
    }
    NativeObject obj = NativeObject.instanceFor(gptr.getPointer());
    if (obj != null && cls.isInstance(obj)) {
      if (ownsHandle && !obj.handle.ownsReference()) {
        obj.handle.ownsReference.set(true);
      } else if (refAdjust < 0) {
        try {
          ((RefCountedObject.Handle) obj.handle).unref();
        } catch (ClassCastException ex) {
          LOG.log(LIFECYCLE, "None ref-counted object returned again from caller owns return.", ex);
        }
      }
      return cls.cast(obj);
    }
    final GType gtype = gptr instanceof GTypedPtr ? ((GTypedPtr) gptr).getGType() : null;
    if (gtype != null) {
      TypeRegistration<?> reg = GstTypes.registrationFor(gtype);
      if (reg != null) {
        return cls.cast(reg.factory.apply(new Initializer(gptr, refAdjust > 0, ownsHandle)));
      }
    }
    LOG.log(Level.FINE, () -> String.format("Unregistered type requested : %s", cls.getSimpleName()));
    try {
      Constructor<T> constructor = cls.getDeclaredConstructor(Initializer.class);
      constructor.setAccessible(true);
      T retVal = constructor.newInstance(new Initializer(gptr, refAdjust > 0, ownsHandle));
      return retVal;
    } catch (SecurityException ex) {
      throw new RuntimeException(ex);
    } catch (IllegalAccessException ex) {
      throw new RuntimeException(ex);
    } catch (InstantiationException ex) {
      throw new RuntimeException(ex);
    } catch (NoSuchMethodException ex) {
      throw new RuntimeException(ex);
    } catch (InvocationTargetException ex) {
      throw new RuntimeException(ex);
    }
  }

  static NativeObject instanceFor(Pointer ptr) {
    WeakReference<NativeObject> ref = INSTANCES.get(ptr);
    if (ref != null && ref.get() == null) {
      INSTANCES.remove(ptr);
    }
    return ref != null ? ref.get() : null;
  }

  public static final class Initializer {
    public final GPointer ptr;
    public final boolean needRef, ownsHandle;

    Initializer(GPointer ptr, boolean needRef, boolean ownsHandle) {
      this.ptr = ptr;
      this.needRef = needRef;
      this.ownsHandle = ownsHandle;
    }
  }

  private static final class NativeRef extends WeakReference<NativeObject> {
    private static final boolean REAP_ON_EDT = Boolean.getBoolean("glib.reapOnEDT");
    private static final ReferenceQueue<NativeObject> QUEUE = new ReferenceQueue<>();
    private static final ExecutorService REAPER = Executors.newSingleThreadExecutor((r) -> {
      Thread t = new Thread(r, "NativeObject Reaper");
      t.setDaemon(true);
      return t;
    });

    static {
      REAPER.submit(() -> {
        while (true) {
          try {
            NativeRef ref = (NativeRef) QUEUE.remove();
            LOG.log(LIFECYCLE, () -> "Disposing of " + ref.type + " : " + ref.handle.ptrRef.get());
            if (REAP_ON_EDT) {
              Gst.invokeLater(ref.handle::dispose);
            } else {
              ref.handle.dispose();
            }
          } catch (Throwable t) {
            LOG.log(Level.WARNING, "Reaper thread exception", t);
          }
        }
      });
    }

    private final Handle handle;
    private final String type;

    private NativeRef(NativeObject obj, Handle handle) {
      super(obj, QUEUE);
      this.type = obj.getClass().getSimpleName();
      this.handle = handle;
    }
  }

  protected abstract static class Handle {
    private final AtomicReference<GPointer> ptrRef;
    private final AtomicBoolean ownsReference;

    public Handle(GPointer ptr, boolean ownsReference) {
      this.ptrRef = new AtomicReference<>(ptr);
      this.ownsReference = new AtomicBoolean(ownsReference);
    }

    public void disown() {
      ownsReference.set(false);
    }

    public void invalidate() {
      GPointer ptr = ptrRef.getAndSet(null);
      ownsReference.set(false);
      if (ptr != null) {
        INSTANCES.remove(ptr.getPointer());
      }
    }

    public void dispose() {
      GPointer ptr = ptrRef.getAndSet(null);
      if (ptr != null) {
        INSTANCES.remove(ptr.getPointer());
        if (ownsReference.compareAndSet(true, false)) {
          disposeNativeHandle(ptr);
        }
      }
    }

    public boolean isCacheable() {
      return true;
    }

    protected abstract void disposeNativeHandle(GPointer ptr);

    protected GPointer getPointer() {
      return ptrRef.get();
    }

    protected boolean ownsReference() {
      return ownsReference.get();
    }
  }

  public static class TypeRegistration<T extends NativeObject> {
    private final Class<T> javaType;
    private final String gTypeName;
    private final Function<Initializer, ? extends T> factory;

    TypeRegistration(Class<T> javaType, String gTypeName, Function<Initializer, ? extends T> factory) {
      this.javaType = javaType;
      this.gTypeName = gTypeName;
      this.factory = factory;
    }

    public Class<T> getJavaType() {
      return javaType;
    }

    public String getGTypeName() {
      return gTypeName;
    }

    public Function<Initializer, ? extends T> getFactory() {
      return factory;
    }
  }

  public static interface TypeProvider {
    public Stream<TypeRegistration<?>> types();
  }
}
