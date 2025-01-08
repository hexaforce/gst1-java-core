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

import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.glib.Natives;

public class SegmentDoneMessage extends Message {
  SegmentDoneMessage(Initializer init) {
    super(init);
  }

  public SegmentDoneMessage(GstObject src, Format format, long position) {
    this(Natives.initializer(GSTMESSAGE_API.ptr_gst_message_new_segment_done(src, format, position)));
  }

  public Format getFormat() {
    Format[] format = new Format[1];
    GSTMESSAGE_API.gst_message_parse_segment_done(this, format, null);
    return format[0];
  }

  public long getPosition() {
    long[] position = {0};
    GSTMESSAGE_API.gst_message_parse_segment_done(this, null, position);
    return position[0];
  }
}
