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
import java.util.Collections;

public interface GioAPI extends Library {
  public static final GioAPI GIO_API = GNative.loadLibrary("gio-2.0", GioAPI.class, Collections.emptyMap());

  public String g_inet_address_to_string(Pointer gInetAddress);

  public Pointer g_inet_socket_address_new_from_string(String address, int port);

  public Pointer g_socket_new(int gSocketFamilyEnumValue, int gSocketTypeEnumValue, int gSocketProtcolEnumValue, Pointer gErrorStructArrayPointer);

  public boolean g_socket_bind(Pointer gSocketPointer, Pointer gSocketAddressPointer, boolean allowReuse, Pointer gErrorStructArrayPointer);

  public boolean g_socket_connect(Pointer gSocketPointer, Pointer gSocketAddressPointer, Pointer gCancellablePointer, Pointer gErrorStructArrayPointer);

  public Pointer g_cancellable_new();
}
