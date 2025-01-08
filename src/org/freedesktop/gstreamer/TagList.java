package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GlibAPI.GLIB_API;
import static org.freedesktop.gstreamer.lowlevel.GstTagAPI.GSTTAG_API;
import static org.freedesktop.gstreamer.lowlevel.GstTagListAPI.GSTTAGLIST_API;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.util.AbstractList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.freedesktop.gstreamer.glib.GDate;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.freedesktop.gstreamer.lowlevel.GstTagListAPI;

public class TagList extends MiniObject {
  public static final String GTYPE_NAME = "GstTagList";

  TagList(Initializer init) {
    super(init);
  }

  public TagList() {
    super(initializer());
  }

  private static Initializer initializer() {
    final Pointer ptr_new_tag_list = GSTTAGLIST_API.ptr_gst_tag_list_new_empty();
    return Natives.initializer(ptr_new_tag_list);
  }

  public int getValueCount(String tag) {
    return GSTTAGLIST_API.gst_tag_list_get_tag_size(this, tag);
  }

  public List<Object> getValues(final String tag) {
    final int size = getValueCount(tag);
    return new AbstractList<Object>() {
      public int size() {
        return size;
      }

      @Override
      public Object get(int index) {
        return getValue(tag, index);
      }
    };
  }

  public Object getValue(String tag, int index) {
    TagGetter get = MapHolder.getterMap.get(getTagType(tag));
    return get != null ? get.get(this, tag, index) : "";
  }

  public String getString(String tag, int index) {
    return getValue(tag, index).toString();
  }

  public Number getNumber(String tag, int index) {
    Object data = getValue(tag, index);
    if (!(data instanceof Number)) {
      throw new IllegalArgumentException("Tag [" + tag + "] is not a number");
    }
    return (Number) data;
  }

  public List<String> getTagNames() {
    final List<String> list = new LinkedList<String>();
    GSTTAGLIST_API.gst_tag_list_foreach(this, new GstTagListAPI.TagForeachFunc() {
      public void callback(Pointer ptr, String tag, Pointer user_data) {
        list.add(tag);
      }
    }, null);
    return list;
  }

  public TagList merge(TagList list2, TagMergeMode mode) {
    return GSTTAGLIST_API.gst_tag_list_merge(this, list2, mode);
  }

  private static GType getTagType(String tag) {
    GType type = MapHolder.tagTypeMap.get(tag);
    if (type != null) {
      return type;
    }
    MapHolder.tagTypeMap.put(tag, type = GSTTAG_API.gst_tag_get_type(tag));
    return type;
  }

  private static interface TagGetter {
    Object get(TagList tl, String tag, int index);
  }

  private static final class MapHolder {
    @SuppressWarnings("serial")
    private static final Map<GType, TagGetter> getterMap = new HashMap<>() {
      {
        put(GType.INT, new TagGetter() {
          public Object get(TagList tl, String tag, int index) {
            int[] value = {0};
            GSTTAGLIST_API.gst_tag_list_get_int_index(tl, tag, index, value);
            return value[0];
          }
        });
        put(GType.UINT, new TagGetter() {
          public Object get(TagList tl, String tag, int index) {
            int[] value = {0};
            GSTTAGLIST_API.gst_tag_list_get_uint_index(tl, tag, index, value);
            return value[0];
          }
        });
        put(GType.INT64, new TagGetter() {
          public Object get(TagList tl, String tag, int index) {
            long[] value = {0};
            GSTTAGLIST_API.gst_tag_list_get_int64_index(tl, tag, index, value);
            return value[0];
          }
        });
        put(GType.DOUBLE, new TagGetter() {
          public Object get(TagList tl, String tag, int index) {
            double[] value = {0d};
            GSTTAGLIST_API.gst_tag_list_get_double_index(tl, tag, index, value);
            return value[0];
          }
        });
        put(GType.STRING, new TagGetter() {
          public Object get(TagList tl, String tag, int index) {
            Pointer[] value = {null};
            GSTTAGLIST_API.gst_tag_list_get_string_index(tl, tag, index, value);
            if (value[0] == null) {
              return null;
            }
            String ret = value[0].getString(0);
            GLIB_API.g_free(value[0]);
            return ret;
          }
        });
        put(GType.valueOf(GDate.GTYPE_NAME), new TagGetter() {
          public Object get(TagList tl, String tag, int index) {
            PointerByReference value = new PointerByReference();
            GSTTAGLIST_API.gst_tag_list_get_date_index(tl, tag, index, value);
            if (value.getValue() == null) {
              return null;
            }
            return Natives.objectFor(value.getValue(), GDate.class, false, true);
          }
        });
        put(GType.valueOf(DateTime.GTYPE_NAME), new TagGetter() {
          public Object get(TagList tl, String tag, int index) {
            PointerByReference value = new PointerByReference();
            GSTTAGLIST_API.gst_tag_list_get_date_time_index(tl, tag, index, value);
            if (value.getValue() == null) {
              return null;
            }
            return new DateTime(value.getValue(), false, true);
          }
        });
      }
    };

    private static final Map<String, GType> tagTypeMap = new ConcurrentHashMap<String, GType>();
  }
}
