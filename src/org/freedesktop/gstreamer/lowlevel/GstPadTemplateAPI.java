package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Pad;
import org.freedesktop.gstreamer.PadDirection;
import org.freedesktop.gstreamer.PadPresence;
import org.freedesktop.gstreamer.PadTemplate;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;
import org.freedesktop.gstreamer.lowlevel.annotations.IncRef;

public interface GstPadTemplateAPI extends com.sun.jna.Library {
  GstPadTemplateAPI GSTPADTEMPLATE_API = GstNative.load(GstPadTemplateAPI.class);

  void gst_element_class_add_pad_template(Pointer klass, PadTemplate templ);

  PadTemplate gst_element_class_get_pad_template(Pointer element_class, String name);

  GType gst_pad_template_get_type();

  GType gst_static_pad_template_get_type();

  @CallerOwnsReturn Pointer ptr_gst_pad_template_new(String name_template, PadDirection direction, PadPresence presence, @IncRef Caps caps);

  @CallerOwnsReturn PadTemplate gst_pad_template_new(String name_template, PadDirection direction, PadPresence presence, @IncRef Caps caps);

  @CallerOwnsReturn PadTemplate gst_static_pad_template_get(GstStaticPadTemplate pad_template);

  @CallerOwnsReturn Caps gst_static_pad_template_get_caps(GstStaticPadTemplate template);

  @CallerOwnsReturn Caps gst_pad_template_get_caps(PadTemplate template);

  void gst_pad_template_pad_created(PadTemplate templ, Pad pad);

  public static final class GstStaticPadTemplate extends PointerType {
    public GstStaticPadTemplate() {}

    public GstStaticPadTemplate(Pointer p) {
      super(p);
    }

    public String getName() {
      return getPointer().getPointer(0).getString(0);
    }

    public PadDirection getPadDirection() {
      return PadDirection.values()[getPointer().getInt(Native.POINTER_SIZE)];
    }

    public PadPresence getPadPresence() {
      return PadPresence.values()[getPointer().getInt(Native.POINTER_SIZE + 4)];
    }
  }
}
