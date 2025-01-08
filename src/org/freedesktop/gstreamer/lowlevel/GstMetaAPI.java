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

package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface GstMetaAPI extends Library {
  GstMetaAPI GST_META_API = GstNative.load(GstMetaAPI.class);

  @Structure.FieldOrder({"flags", "info"})
  class GstMetaStruct extends Structure {
    public static final class ByValue extends GstMetaStruct implements Structure.ByValue {}

    public int flags;
    public GstMetaInfoStruct.ByReference info;

    int infoOffset() {
      return fieldOffset("info");
    }
  }

  @Structure.FieldOrder({"api", "type", "size"})
  class GstMetaInfoStruct extends Structure {
    public static class ByReference extends GstMetaInfoStruct implements Structure.ByReference {}

    public GstMetaInfoStruct() {}

    public GstMetaInfoStruct(Pointer p) {
      super(p);
      read();
    }

    public GType api;
    public GType type;
    public long size;

    int typeOffset() {
      return fieldOffset("type");
    }
  }
}
