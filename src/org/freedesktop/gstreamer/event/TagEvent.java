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

package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.TagList;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.ReferenceManager;

public class TagEvent extends Event {
  TagEvent(Initializer init) {
    super(init);
  }

  public TagEvent(TagList taglist) {
    this(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_tag(taglist)));
  }

  public TagList getTagList() {
    Pointer[] taglist = new Pointer[1];
    GSTEVENT_API.gst_event_parse_tag(this, taglist);
    TagList tl = Natives.objectFor(taglist[0], TagList.class, false, false);
    ReferenceManager.addKeepAliveReference(tl, this);
    return tl;
  }
}
