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
