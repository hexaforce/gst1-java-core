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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.freedesktop.gstreamer.glib.NativeObject;
import org.freedesktop.gstreamer.glib.NativeObject.TypeRegistration;

public class GstTypes {
  private static final Logger logger = Logger.getLogger(GstTypes.class.getName());
  private static final Map<String, TypeRegistration<?>> TYPES = new ConcurrentHashMap<>();

  private GstTypes() {}

  public static void register(TypeRegistration<?> registration) {
    TYPES.putIfAbsent(registration.getGTypeName(), registration);
  }

  public static final TypeRegistration<?> registrationFor(final GType gType) {
    final String gTypeName = gType.getTypeName();
    TypeRegistration<?> reg = TYPES.get(gTypeName);
    if (reg != null) {
      return reg;
    }
    GType type = gType.getParentType();
    while (!type.equals(GType.OBJECT) && !type.equals(GType.POINTER) && !type.equals(GType.INVALID)) {
      reg = TYPES.get(type.getTypeName());
      if (reg != null) {
        if (GstTypes.logger.isLoggable(Level.FINER)) {
          GstTypes.logger.finer("Found type of " + gType + " = " + reg.getJavaType());
        }
        return reg;
      }
      type = type.getParentType();
    }
    return null;
  }

  public static final Class<? extends NativeObject> classFor(final GType gType) {
    TypeRegistration<?> reg = registrationFor(gType);
    return reg != null ? reg.getJavaType() : null;
  }

  public static final GType typeFor(Class<? extends NativeObject> cls) {
    for (Map.Entry<String, TypeRegistration<?>> e : TYPES.entrySet()) {
      if (e.getValue().getJavaType().equals(cls)) {
        return GType.valueOf(e.getKey());
      }
    }
    return GType.INVALID;
  }
}
