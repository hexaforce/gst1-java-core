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

import com.sun.jna.ptr.PointerByReference;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.TagList;
import org.freedesktop.gstreamer.glib.Natives;

public class TagMessage extends Message {
  TagMessage(Initializer init) {
    super(init);
  }

  public TagMessage(GstObject src, TagList tagList) {
    this(Natives.initializer(GSTMESSAGE_API.ptr_gst_message_new_tag(src, tagList)));
  }

  public TagList getTagList() {
    PointerByReference list = new PointerByReference();
    GSTMESSAGE_API.gst_message_parse_tag(this, list);
    return Natives.objectFor(list.getValue(), TagList.class, false, true);
  }
}
