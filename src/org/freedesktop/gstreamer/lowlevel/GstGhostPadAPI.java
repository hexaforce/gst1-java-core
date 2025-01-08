package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.GhostPad;
import org.freedesktop.gstreamer.Pad;
import org.freedesktop.gstreamer.PadTemplate;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

public interface GstGhostPadAPI extends com.sun.jna.Library {
  GstGhostPadAPI GSTGHOSTPAD_API = GstNative.load(GstGhostPadAPI.class);

  GType gst_ghost_pad_get_type();

  @CallerOwnsReturn Pointer ptr_gst_ghost_pad_new(String name, Pad target);

  @CallerOwnsReturn Pointer ptr_gst_ghost_pad_new_no_target(String name, int direction);

  @CallerOwnsReturn Pointer ptr_gst_ghost_pad_new_from_template(String name, Pad target, PadTemplate templ);

  @CallerOwnsReturn Pointer ptr_gst_ghost_pad_new_no_target_from_template(String name, PadTemplate templ);

  @CallerOwnsReturn GhostPad gst_ghost_pad_new(String name, Pad target);

  @CallerOwnsReturn GhostPad gst_ghost_pad_new_no_target(String name, int direction);

  @CallerOwnsReturn GhostPad gst_ghost_pad_new_from_template(String name, Pad target, PadTemplate templ);

  @CallerOwnsReturn GhostPad gst_ghost_pad_new_no_target_from_template(String name, PadTemplate templ);

  @CallerOwnsReturn Pad gst_ghost_pad_get_target(GhostPad gpad);

  boolean gst_ghost_pad_set_target(GhostPad gpad, Pad newtarget);
}
