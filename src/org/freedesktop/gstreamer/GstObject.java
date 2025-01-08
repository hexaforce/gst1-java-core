package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GObjectAPI.GOBJECT_API;
import static org.freedesktop.gstreamer.lowlevel.GValueAPI.GVALUE_API;
import static org.freedesktop.gstreamer.lowlevel.GlibAPI.GLIB_API;
import static org.freedesktop.gstreamer.lowlevel.GstObjectAPI.GSTOBJECT_API;
import static org.freedesktop.gstreamer.lowlevel.GstValueAPI.GSTVALUE_API;

import com.sun.jna.Pointer;
import java.util.logging.Logger;
import org.freedesktop.gstreamer.glib.GObject;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GObjectAPI;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.freedesktop.gstreamer.lowlevel.GValueAPI.GValue;
import org.freedesktop.gstreamer.lowlevel.GstControlBindingPtr;
import org.freedesktop.gstreamer.lowlevel.GstObjectPtr;

public class GstObject extends GObject {
  private static Logger LOG = Logger.getLogger(GstObject.class.getName());
  private final Handle handle;

  protected GstObject(Initializer init) {
    this(new Handle(init.ptr.as(GstObjectPtr.class, GstObjectPtr::new), init.ownsHandle), init.needRef);
  }

  protected GstObject(Handle handle, boolean needRef) {
    super(handle, needRef);
    this.handle = handle;
  }

  public void setAsString(String property, String data) {
    GObjectAPI.GParamSpec propertySpec = findProperty(property);
    if (propertySpec == null) {
      throw new IllegalArgumentException("Unknown property: " + property);
    }
    final GType propType = propertySpec.value_type;
    GValue propValue = new GValue();
    GVALUE_API.g_value_init(propValue, propType);
    boolean success = GSTVALUE_API.gst_value_deserialize(propValue, data);
    if (success) {
      GOBJECT_API.g_param_value_validate(propertySpec, propValue);
      GOBJECT_API.g_object_set_property(this, property, propValue);
    }
    GVALUE_API.g_value_unset(propValue);
    if (!success) {
      throw new IllegalArgumentException("Unable to deserialize data to required type: " + propType.getTypeName());
    }
  }

  public String getAsString(String property) {
    GObjectAPI.GParamSpec propertySpec = findProperty(property);
    if (propertySpec == null) {
      throw new IllegalArgumentException("Unknown property: " + property);
    }
    final GType propType = propertySpec.value_type;
    GValue propValue = new GValue();
    GVALUE_API.g_value_init(propValue, propType);
    GOBJECT_API.g_object_get_property(this, property, propValue);
    Pointer ptr = GSTVALUE_API.gst_value_serialize(propValue);
    String ret = ptr.getString(0);
    GLIB_API.g_free(ptr);
    return ret;
  }

  private GObjectAPI.GParamSpec findProperty(String propertyName) {
    Pointer ptr = GOBJECT_API.g_object_class_find_property(getRawPointer().getPointer(0), propertyName);
    if (ptr == null) {
      return null;
    }
    return new GObjectAPI.GParamSpec(ptr);
  }

  public boolean setName(String name) {
    LOG.entering("GstObject", "setName", name);
    return GSTOBJECT_API.gst_object_set_name(this, name);
  }

  public String getName() {
    LOG.entering("GstObject", "getName");
    return GSTOBJECT_API.gst_object_get_name(this);
  }

  public GstObject getParent() {
    return GSTOBJECT_API.gst_object_get_parent(this);
  }

  public long suggestNextSync() {
    return GSTOBJECT_API.gst_object_suggest_next_sync(handle.getPointer());
  }

  public boolean syncValues(long timestamp) {
    return GSTOBJECT_API.gst_object_sync_values(handle.getPointer(), timestamp);
  }

  public boolean hasActiveControlBindings() {
    return GSTOBJECT_API.gst_object_has_active_control_bindings(handle.getPointer());
  }

  public void setControlBindingsDisabled(boolean disabled) {
    GSTOBJECT_API.gst_object_set_control_bindings_disabled(handle.getPointer(), disabled);
  }

  public void setControlBindingDisabled(String propertyName, boolean disabled) {
    GSTOBJECT_API.gst_object_set_control_binding_disabled(handle.getPointer(), propertyName, disabled);
  }

  public void addControlBinding(ControlBinding binding) {
    GstControlBindingPtr bindingPtr = Natives.getPointer(binding).as(GstControlBindingPtr.class, GstControlBindingPtr::new);
    boolean ok = GSTOBJECT_API.gst_object_add_control_binding(handle.getPointer(), bindingPtr);
    if (!ok) {
      throw new IllegalStateException();
    }
  }

  public ControlBinding getControlBinding(String propertyName) {
    GstControlBindingPtr ptr = GSTOBJECT_API.gst_object_get_control_binding(handle.getPointer(), propertyName);
    return ptr == null ? null : Natives.callerOwnsReturn(ptr, ControlBinding.class);
  }

  public boolean removeControlBinding(ControlBinding binding) {
    GstControlBindingPtr bindingPtr = Natives.getPointer(binding).as(GstControlBindingPtr.class, GstControlBindingPtr::new);
    return GSTOBJECT_API.gst_object_remove_control_binding(handle.getPointer(), bindingPtr);
  }

  @Override
  public String toString() {
    return String.format("%s: [%s]", getClass().getSimpleName(), getName());
  }

  protected static class Handle extends GObject.Handle {
    public Handle(GstObjectPtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected void ref() {
      GSTOBJECT_API.gst_object_ref(getPointer());
    }

    @Override
    protected void sink() {
      GSTOBJECT_API.gst_object_ref_sink(getPointer());
    }

    @Override
    protected void unref() {
      GSTOBJECT_API.gst_object_unref(getPointer());
    }

    @Override
    protected GstObjectPtr getPointer() {
      return (GstObjectPtr) super.getPointer();
    }
  }
}
