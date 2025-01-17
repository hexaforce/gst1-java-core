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

package org.freedesktop.gstreamer.message;

import static org.freedesktop.gstreamer.lowlevel.GlibAPI.GLIB_API;

import org.freedesktop.gstreamer.lowlevel.GstAPI;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GErrorStruct;

abstract class GErrorMessage extends Message {
  GErrorMessage(Initializer init) {
    super(init);
  }

  abstract GstAPI.GErrorStruct parseMessage();

  public int getCode() {
    GErrorStruct err = parseMessage();
    if (err == null) {
      throw new NullPointerException("Could not parse message");
    }
    int code = err.code;
    GLIB_API.g_error_free(err);
    return code;
  }

  public String getMessage() {
    GErrorStruct err = parseMessage();
    if (err == null) {
      throw new NullPointerException("Could not parse message");
    }
    String message = err.getMessage();
    GLIB_API.g_error_free(err);
    return message;
  }
}
