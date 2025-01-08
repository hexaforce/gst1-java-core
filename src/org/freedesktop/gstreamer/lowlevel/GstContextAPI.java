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

import org.freedesktop.gstreamer.Structure;

public interface GstContextAPI extends com.sun.jna.Library {
  GstContextAPI GSTCONTEXT_API = GstNative.load(GstContextAPI.class);

  GstContextPtr gst_context_new(String context_type, boolean persistent);

  String gst_context_get_context_type(GstContextPtr context);

  boolean gst_context_has_context_type(GstContextPtr context, String context_type);

  Structure gst_context_get_structure(GstContextPtr context);

  Structure gst_context_writable_structure(GstContextPtr context);

  boolean gst_context_is_persistent(GstContextPtr context);
}
