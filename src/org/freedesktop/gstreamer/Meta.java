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

package org.freedesktop.gstreamer;

import java.util.Objects;
import org.freedesktop.gstreamer.glib.NativeObject;
import org.freedesktop.gstreamer.lowlevel.GPointer;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.freedesktop.gstreamer.lowlevel.GstMetaPtr;

public class Meta extends NativeObject {
  protected Meta(Initializer init) {
    this(new Handle(init.ptr.as(GstMetaPtr.class, GstMetaPtr::new), init.ownsHandle));
  }

  protected Meta(Handle handle) {
    super(handle);
  }

  @Override
  public String toString() {
    GstMetaPtr pointer = (GstMetaPtr) this.getPointer();
    return "[meta : gType=" + pointer.getGType() + "]";
  }

  protected static class Handle extends NativeObject.Handle {
    public Handle(GstMetaPtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {}
  }

  public static final class API<T extends Meta> {
    private final Class<T> implClass;
    private final String apiTypeName;
    private GType apiType;

    public API(Class<T> impl, String api) {
      this.implClass = Objects.requireNonNull(impl);
      this.apiTypeName = Objects.requireNonNull(api);
      apiType = GType.INVALID;
    }

    Class<T> getImplClass() {
      return implClass;
    }

    GType getAPIGType() {
      GType type = apiType;
      if (type == GType.INVALID) {
        type = GType.valueOf(apiTypeName);
        apiType = type;
      }
      return type;
    }
  }
}
