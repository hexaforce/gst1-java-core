package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.freedesktop.gstreamer.glib.GObject;
import org.freedesktop.gstreamer.glib.GQuark;
import org.freedesktop.gstreamer.lowlevel.GObjectAPI.GClosureNotify;

@SuppressWarnings("serial")
public interface GSignalAPI extends Library {
  GSignalAPI GSIGNAL_API = GNative.loadLibrary("gobject-2.0", GSignalAPI.class, new HashMap<String, Object>() {
    {
      put(Library.OPTION_TYPE_MAPPER, new GTypeMapper());
    }
  });
  public static int G_CONNECT_AFTER = 1 << 0;
  public static int G_CONNECT_SWAPPED = 1 << 1;

  public static final class GSignalQuery extends com.sun.jna.Structure {
    public int signal_id;
    public String signal_name;
    public GType itype;
    public int signal_flags;
    public GType return_type;
    public int n_params;
    public Pointer param_types;

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"signal_id", "signal_name", "itype", "signal_flags", "return_type", "n_params", "param_types"});
    }
  }

  NativeLong g_signal_connect_data(GObject obj, String signal, Callback callback, Pointer data, GClosureNotify destroy_data, int connect_flags);

  void g_signal_handler_disconnect(GObject obj, NativeLong id);

  int g_signal_lookup(String name, GType itype);

  String g_signal_name(int signal_id);

  void g_signal_query(int signal_id, GSignalQuery query);

  int g_signal_list_ids(GType itype, int[] n_ids);

  void g_signal_emit(GObject obj, int signal_id, GQuark detail, Object... arguments);

  void g_signal_emit_by_name(GObject obj, String signal, Object... arguments);

  public static interface GSignalCallbackProxy extends com.sun.jna.CallbackProxy {}
}
