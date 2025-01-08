package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstGhostPadAPI.GSTGHOSTPAD_API;

import org.freedesktop.gstreamer.glib.Natives;

public class GhostPad extends Pad {
  public static final String GTYPE_NAME = "GstGhostPad";

  GhostPad(Initializer init) {
    super(init);
  }

  public GhostPad(String name, Pad target) {
    this(Natives.initializer(GSTGHOSTPAD_API.ptr_gst_ghost_pad_new(name, target)));
  }

  public GhostPad(String name, Pad target, PadTemplate template) {
    this(Natives.initializer(GSTGHOSTPAD_API.ptr_gst_ghost_pad_new_from_template(name, target, template)));
  }

  public GhostPad(String name, PadDirection direction) {
    this(Natives.initializer(GSTGHOSTPAD_API.ptr_gst_ghost_pad_new_no_target(name, direction.ordinal())));
  }

  public GhostPad(String name, PadTemplate template) {
    this(Natives.initializer(GSTGHOSTPAD_API.ptr_gst_ghost_pad_new_no_target_from_template(name, template)));
  }

  public Pad getTarget() {
    return GSTGHOSTPAD_API.gst_ghost_pad_get_target(this);
  }

  public boolean setTarget(Pad pad) {
    return GSTGHOSTPAD_API.gst_ghost_pad_set_target(this, pad);
  }
}
