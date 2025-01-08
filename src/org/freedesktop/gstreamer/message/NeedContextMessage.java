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

import static org.freedesktop.gstreamer.lowlevel.GstMessageAPI.GSTMESSAGE_API;

import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.glib.Natives;

public class NeedContextMessage extends Message {
  NeedContextMessage(Initializer init) {
    super(init);
  }

  public NeedContextMessage(GstObject src, String context_type) {
    this(Natives.initializer(GSTMESSAGE_API.ptr_gst_message_new_need_context(src, context_type)));
  }

  public String getContextType() {
    String context_type[] = new String[1];
    boolean isOk = GSTMESSAGE_API.gst_message_parse_context_type(this, context_type);
    return isOk ? context_type[0] : null;
  }
}
