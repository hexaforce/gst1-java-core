package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GstStructureAPI.GSTSTRUCTURE_API;
import static org.freedesktop.gstreamer.lowlevel.GstValueAPI.GSTVALUE_API;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.freedesktop.gstreamer.glib.GObject;
import org.freedesktop.gstreamer.glib.NativeObject;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GPointer;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.freedesktop.gstreamer.lowlevel.GValueAPI;
import org.freedesktop.gstreamer.lowlevel.GValueAPI.GValue;

public class Structure extends NativeObject {
  public static final String GTYPE_NAME = "GstStructure";

  public Structure(String name) {
    this(new Handle(new GPointer(GSTSTRUCTURE_API.ptr_gst_structure_new_empty(name)), true));
  }

  public Structure(String name, String firstFieldName, Object... data) {
    this(new Handle(new GPointer(GSTSTRUCTURE_API.ptr_gst_structure_new(name, firstFieldName, data)), true));
  }

  Structure(Initializer init) {
    this(new Handle(init.ptr, init.ownsHandle));
  }

  private Structure(Handle handle) {
    super(handle);
  }

  public Structure copy() {
    return GSTSTRUCTURE_API.gst_structure_copy(this);
  }

  public boolean fixateNearestInteger(String field, Integer value) {
    return GSTSTRUCTURE_API.gst_structure_fixate_field_nearest_int(this, field, value);
  }

  public boolean getBoolean(String fieldName) {
    int[] val = {0};
    if (!GSTSTRUCTURE_API.gst_structure_get_boolean(this, fieldName, val)) {
      throw new InvalidFieldException("boolean", fieldName);
    }
    return val[0] != 0;
  }

  public double getDouble(String fieldName) {
    double[] val = {0d};
    if (!GSTSTRUCTURE_API.gst_structure_get_double(this, fieldName, val)) {
      throw new InvalidFieldException("double", fieldName);
    }
    return val[0];
  }

  public double[] getDoubles(String fieldName) {
    return getDoubles(fieldName, null);
  }

  public double[] getDoubles(String fieldName, double[] array) {
    Object val = getValue(fieldName);
    if (val instanceof GValueAPI.GValueArray) {
      GValueAPI.GValueArray arr = (GValueAPI.GValueArray) val;
      int count = arr.getNValues();
      double[] values = array == null || array.length != count ? new double[count] : array;
      for (int i = 0; i < count; i++) {
        GValue gval = arr.nth(i);
        if (gval.checkHolds(GType.DOUBLE)) {
          values[i] = GValueAPI.GVALUE_API.g_value_get_double(gval);
        } else {
          throw new InvalidFieldException("doubles", fieldName);
        }
      }
      return values;
    } else {
      if (Double.class.isInstance(val)) {
        double[] values = array == null || array.length != 1 ? new double[1] : array;
        values[0] = ((Double) val);
        return values;
      } else {
        throw new InvalidFieldException("double", fieldName);
      }
    }
  }

  public int getFields() {
    return GSTSTRUCTURE_API.gst_structure_n_fields(this);
  }

  public int getFourcc(String fieldName) {
    int[] val = {0};
    if (!GSTSTRUCTURE_API.gst_structure_get_fourcc(this, fieldName, val)) {
      throw new InvalidFieldException("FOURCC", fieldName);
    }
    return val[0];
  }

  public String getFourccString(String fieldName) {
    int f = getFourcc(fieldName);
    byte[] b = {(byte) ((f >> 0) & 0xff), (byte) ((f >> 8) & 0xff), (byte) ((f >> 16) & 0xff), (byte) ((f >> 24) & 0xff)};
    return new String(b);
  }

  public Fraction getFraction(String fieldName) {
    int[] numerator = {0};
    int[] denominator = {0};
    if (!GSTSTRUCTURE_API.gst_structure_get_fraction(this, fieldName, numerator, denominator)) {
      throw new InvalidFieldException("fraction", fieldName);
    }
    return new Fraction(numerator[0], denominator[0]);
  }

  public int getInteger(String fieldName) {
    int[] val = {0};
    if (!GSTSTRUCTURE_API.gst_structure_get_int(this, fieldName, val)) {
      throw new InvalidFieldException("integer", fieldName);
    }
    return val[0];
  }

  public int[] getIntegers(String fieldName) {
    return getIntegers(fieldName, null);
  }

  public int[] getIntegers(String fieldName, int[] array) {
    Object val = getValue(fieldName);
    if (val instanceof GValueAPI.GValueArray) {
      GValueAPI.GValueArray arr = (GValueAPI.GValueArray) val;
      int count = arr.getNValues();
      int[] values = array == null || array.length != count ? new int[count] : array;
      for (int i = 0; i < count; i++) {
        GValue gval = arr.nth(i);
        if (gval.checkHolds(GType.INT)) {
          values[i] = GValueAPI.GVALUE_API.g_value_get_int(gval);
        } else {
          throw new InvalidFieldException("integers", fieldName);
        }
      }
      return values;
    } else {
      if (Integer.class.isInstance(val)) {
        int[] values = array == null || array.length != 1 ? new int[1] : array;
        values[0] = ((Integer) val);
        return values;
      } else {
        throw new InvalidFieldException("integer", fieldName);
      }
    }
  }

  public void setName(String name) {
    GSTSTRUCTURE_API.gst_structure_set_name(this, name);
  }

  public String getName() {
    return GSTSTRUCTURE_API.gst_structure_get_name(this);
  }

  public String getName(int i) {
    return GSTSTRUCTURE_API.gst_structure_nth_field_name(this, i);
  }

  public Range getRange(String fieldName) {
    GValue val = GSTSTRUCTURE_API.gst_structure_get_value(this, fieldName);
    if (val == null) {
      throw new InvalidFieldException("Range", fieldName);
    }
    return new Range(val);
  }

  public String getString(String fieldName) {
    return GSTSTRUCTURE_API.gst_structure_get_string(this, fieldName);
  }

  public Object getValue(String fieldName) {
    GValue val = GSTSTRUCTURE_API.gst_structure_get_value(this, fieldName);
    if (val == null) {
      throw new InvalidFieldException("Object", fieldName);
    }
    return val.getValue();
  }

  public <T> List<T> getValues(Class<T> type, String fieldName) {
    GValue gValue = GSTSTRUCTURE_API.gst_structure_get_value(this, fieldName);
    if (gValue == null) {
      throw new InvalidFieldException(type.getSimpleName(), fieldName);
    }
    GType gType = gValue.getType();
    if (gType.equals(GSTVALUE_API.gst_value_list_get_type())) {
      int size = GSTVALUE_API.gst_value_list_get_size(gValue);
      ArrayList<T> values = new ArrayList<>(size);
      for (int i = 0; i < size; i++) {
        Object o = GSTVALUE_API.gst_value_list_get_value(gValue, i).getValue();
        if (type.isInstance(o)) {
          values.add(type.cast(o));
        } else {
          throw new InvalidFieldException(type.getSimpleName(), fieldName);
        }
      }
      return values;
    }
    Object val = gValue.getValue();
    if (val instanceof GValueAPI.GValueArray) {
      GValueAPI.GValueArray arr = (GValueAPI.GValueArray) val;
      int count = arr.getNValues();
      List<T> values = new ArrayList<T>(count);
      for (int i = 0; i < count; i++) {
        Object o = arr.getValue(i);
        if (type.isInstance(o)) {
          values.add(type.cast(o));
        } else {
          throw new InvalidFieldException(type.getSimpleName(), fieldName);
        }
      }
      return values;
    } else {
      if (type.isInstance(val)) {
        return Collections.singletonList(type.cast(val));
      } else {
        throw new InvalidFieldException(type.getSimpleName(), fieldName);
      }
    }
  }

  public boolean hasDoubleField(String fieldName) {
    return hasField(fieldName, GType.DOUBLE);
  }

  public boolean hasField(String fieldName) {
    return GSTSTRUCTURE_API.gst_structure_has_field(this, fieldName);
  }

  boolean hasField(String fieldName, GType fieldType) {
    return GSTSTRUCTURE_API.gst_structure_has_field_typed(this, fieldName, fieldType);
  }

  public boolean hasField(String fieldName, Class<?> fieldType) {
    return GSTSTRUCTURE_API.gst_structure_has_field_typed(this, fieldName, GType.valueOf(fieldType));
  }

  public boolean hasIntField(String fieldName) {
    return hasField(fieldName, GType.INT);
  }

  public boolean hasName(String name) {
    return GSTSTRUCTURE_API.gst_structure_has_name(this, name);
  }

  public boolean isEqual(Structure structure) {
    return GSTSTRUCTURE_API.gst_structure_is_equal(this, structure);
  }

  public void removeField(String fieldName) {
    GSTSTRUCTURE_API.gst_structure_remove_field(this, fieldName);
  }

  public void removeFields(String... fieldNames) {
    GSTSTRUCTURE_API.gst_structure_remove_fields(this, fieldNames);
  }

  public void setDouble(String field, Double value) {
    GSTSTRUCTURE_API.gst_structure_set(this, field, GType.DOUBLE, value);
  }

  public void setDoubleRange(String field, Double min, Double max) {
    GSTSTRUCTURE_API.gst_structure_set(this, field, GSTVALUE_API.gst_double_range_get_type(), min, max);
  }

  public void setFraction(String field, Integer numerator, Integer denominator) {
    GSTSTRUCTURE_API.gst_structure_set(this, field, GSTVALUE_API.gst_fraction_get_type(), numerator, denominator);
  }

  public void setInteger(String field, Integer value) {
    GSTSTRUCTURE_API.gst_structure_set(this, field, GType.INT, value);
  }

  public void setIntegerRange(String field, Integer min, Integer max) {
    GSTSTRUCTURE_API.gst_structure_set(this, field, GSTVALUE_API.gst_int_range_get_type(), min, max);
  }

  public void setObject(String field, String typeName, GObject object) {
    GType type = GType.valueOf(typeName);
    if (!GType.INVALID.equals(type)) {
      if (object != null) {
        GType realType = GType.valueOf(object.getTypeName());
        while (!realType.equals(GType.OBJECT) && !realType.equals(type)) {
          realType = realType.getParentType();
        }
        if (!realType.equals(type)) {
          throw new IllegalArgumentException("Provided instance of " + object.getTypeName() + " is not a " + typeName);
        }
        setValue(field, type, object);
      } else {
        throw new IllegalArgumentException("Null object provided");
      }
    } else {
      throw new IllegalArgumentException("Unknown GType name: " + typeName);
    }
  }

  void setPointer(String field, Pointer value) {
    GSTSTRUCTURE_API.gst_structure_set(this, field, GType.POINTER, value);
  }

  void setValue(String field, GType type, Object value) {
    GSTSTRUCTURE_API.gst_structure_set(this, field, type, value);
  }

  @Override
  public String toString() {
    return GSTSTRUCTURE_API.gst_structure_to_string(this);
  }

  public static Structure fromString(String data) {
    return new Structure(new Handle(new GPointer(GSTSTRUCTURE_API.ptr_gst_structure_from_string(data, new PointerByReference())), true));
  }

  static Structure objectFor(Pointer ptr, boolean needRef, boolean ownsHandle) {
    return Natives.objectFor(ptr, Structure.class, needRef, ownsHandle);
  }

  public class InvalidFieldException extends RuntimeException {
    private static final long serialVersionUID = 864118748304334069L;

    public InvalidFieldException(String type, String fieldName) {
      super(String.format("Structure does not contain %s field '%s'", type, fieldName));
    }
  }

  private static final class Handle extends NativeObject.Handle {
    public Handle(GPointer ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected void disposeNativeHandle(GPointer ptr) {
      GSTSTRUCTURE_API.gst_structure_free(ptr.getPointer());
    }
  }
}
