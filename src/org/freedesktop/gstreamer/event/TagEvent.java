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
