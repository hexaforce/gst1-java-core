package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Callback;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.lowlevel.GlibAPI.GList;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;
import org.freedesktop.gstreamer.lowlevel.annotations.FreeReturnValue;

public interface GstObjectAPI extends com.sun.jna.Library {
  GstObjectAPI GSTOBJECT_API = GstNative.load(GstObjectAPI.class);

  GType gst_object_get_type();

  void gst_object_ref(GstObjectPtr ptr);

  void gst_object_unref(GstObjectPtr ptr);

  void gst_object_ref_sink(GstObjectPtr ptr);

  boolean gst_object_set_name(GstObject obj, String name);

  @FreeReturnValue String gst_object_get_name(GstObject obj);

  void gst_object_set_name_prefix(GstObject object, String name_prefix);

  @FreeReturnValue String gst_object_get_name_prefix(GstObject object);

  boolean gst_object_set_parent(GstObject object, GstObject parent);

  @CallerOwnsReturn GstObject gst_object_get_parent(GstObject object);

  void gst_object_unparent(GstObject object);

  boolean gst_object_has_ancestor(GstObject object, GstObject ancestor);

  Pointer gst_implements_interface_cast(GstObject obj, NativeLong gtype);

  boolean gst_implements_interface_check(GstObject from, NativeLong type);

  long gst_object_suggest_next_sync(GstObjectPtr object);

  boolean gst_object_sync_values(GstObjectPtr object, long timestamp);

  boolean gst_object_has_active_control_bindings(GstObjectPtr object);

  void gst_object_set_control_bindings_disabled(GstObjectPtr object, boolean disabled);

  void gst_object_set_control_binding_disabled(GstObjectPtr object, String property_name, boolean disabled);

  boolean gst_object_add_control_binding(GstObjectPtr object, GstControlBindingPtr binding);

  GstControlBindingPtr gst_object_get_control_binding(GstObjectPtr object, String property_name);

  boolean gst_object_remove_control_binding(GstObjectPtr object, GstControlBindingPtr binding);

  public static final class GstObjectStruct extends com.sun.jna.Structure {
    public GObjectAPI.GObjectStruct object;
    public volatile Pointer lock;
    public volatile String name;
    public volatile Pointer parent;
    public volatile int flags;
    public volatile GList control_bindings;
    public volatile long control_rate;
    public volatile long last_sync;
    public volatile Pointer _gst_reserved;

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"object", "lock", "name", "parent", "flags", "control_bindings", "control_rate", "last_sync", "_gst_reserved"});
    }
  }

  public static interface DeepNotify extends Callback {
    public void callback(GstObject object, GstObject orig, GObjectAPI.GParamSpec pspec);
  }

  public static final class GstObjectClass extends com.sun.jna.Structure {
    public GObjectAPI.GObjectClass parent_class;
    public volatile String path_string_separator;
    public DeepNotify deep_notify;
    public volatile Pointer[] _gst_reserved = new Pointer[GstAPI.GST_PADDING];

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"parent_class", "path_string_separator", "deep_notify", "_gst_reserved"});
    }
  }
}
