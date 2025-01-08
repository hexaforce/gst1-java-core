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

public interface NativeEnum<T extends Enum<T>> {
  public int intValue();

  public static <T extends Enum<T> & NativeEnum<T>> T fromInt(Class<T> type, int intValue) {
    for (T value : type.getEnumConstants()) {
      if (value.intValue() == intValue) {
        return value;
      }
    }
    throw new IllegalArgumentException("Value " + intValue + " is unacceptable for " + type.getSimpleName() + " enum");
  }

  public static <T extends Enum<T> & NativeEnum<T>> T fromInt(Class<T> type, T defValue, int intValue) {
    for (T value : type.getEnumConstants()) {
      if (value.intValue() == intValue) {
        return value;
      }
    }
    return defValue;
  }
}
