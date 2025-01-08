package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.freedesktop.gstreamer.glib.GMainContext;
import org.freedesktop.gstreamer.glib.GSource;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;

@SuppressWarnings("serial")
public interface GlibAPI extends Library {
  GlibAPI GLIB_API = GNative.loadLibrary("glib-2.0", GlibAPI.class, new HashMap<String, Object>() {
    {
      put(Library.OPTION_TYPE_MAPPER, new GTypeMapper());
    }
  });
  public static final int GLIB_SYSDEF_AF_UNIX = 1;
  public static final int GLIB_SYSDEF_AF_INET = 2;
  public static final int GLIB_SYSDEF_AF_INET6 = 23;

  Pointer g_main_loop_new(GMainContext context, boolean running);

  void g_main_loop_run(MainLoop loop);

  boolean g_main_loop_is_running(MainLoop loop);

  @CallerOwnsReturn GMainContext g_main_loop_get_context(MainLoop loop);

  void g_main_loop_quit(MainLoop loop);

  void g_main_loop_ref(GPointer ptr);

  void g_main_loop_unref(GPointer ptr);

  Pointer g_main_context_new();

  void g_main_context_ref(GPointer context);

  void g_main_context_unref(GPointer context);

  Pointer g_main_context_default();

  boolean g_main_context_pending(GMainContext ctx);

  boolean g_main_context_acquire(GMainContext ctx);

  void g_main_context_release(GMainContext ctx);

  boolean g_main_context_is_owner(GMainContext ctx);

  boolean g_main_context_wait(GMainContext ctx);

  @CallerOwnsReturn GSource g_idle_source_new();

  @CallerOwnsReturn GSource g_timeout_source_new(int interval);

  @CallerOwnsReturn GSource g_timeout_source_new_seconds(int interval);

  int g_source_attach(GSource source, GMainContext context);

  void g_source_destroy(Pointer source);

  void g_source_destroy(GSource source);

  Pointer g_source_ref(Pointer source);

  void g_source_unref(Pointer source);

  void g_source_set_callback(GSource source, GSourceFunc callback, Object data, GDestroyNotify destroy);

  boolean g_source_is_destroyed(Pointer source);

  boolean g_source_is_destroyed(GSource source);

  interface GThreadFunc extends Callback {
    Pointer callback(Pointer data);
  }

  Pointer g_thread_create(GThreadFunc func, Pointer data, boolean joinable, PointerByReference error);

  Pointer g_thread_self();

  Pointer g_thread_join(Pointer thread);

  void g_thread_yield();

  void g_thread_set_priority(Pointer thread, int priority);

  void g_thread_exit(Pointer retval);

  interface GSourceFunc extends Callback {
    boolean callback(Pointer data);
  }

  NativeLong g_idle_add(GSourceFunc function, Pointer data);

  interface GDestroyNotify extends Callback {
    void callback(Pointer data);
  }

  int g_timeout_add(int interval, GSourceFunc function, Pointer data);

  int g_timeout_add_full(int priority, int interval, GSourceFunc function, Pointer data, GDestroyNotify notify);

  int g_timeout_add_seconds(int interval, GSourceFunc function, Pointer data);

  GstAPI.GErrorStruct g_error_new(int quark, int code, String message);

  void g_error_free(Pointer error);

  void g_error_free(GstAPI.GErrorStruct error);

  void g_source_remove(int id);

  void g_free(Pointer ptr);

  Pointer g_date_new();

  Pointer g_date_new_dmy(int day, int month, int year);

  Pointer g_date_new_julian(int julian_day);

  int g_date_get_year(Pointer date);

  int g_date_get_month(Pointer date);

  int g_date_get_day(Pointer date);

  void g_date_free(Pointer date);

  GList g_list_append(GList list, Pointer data);

  public static final class GList extends com.sun.jna.Structure {
    public volatile Pointer data;
    public volatile Pointer _next;
    public volatile Pointer _prev;

    public GList() {
      clear();
    }

    public GList(Pointer ptr) {
      useMemory(ptr);
      read();
    }

    public static GList valueOf(Pointer ptr) {
      return ptr != null ? new GList(ptr) : null;
    }

    public GList next() {
      return valueOf(_next);
    }

    public GList prev() {
      return valueOf(_prev);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"data", "_next", "_prev"});
    }
  }

  public static final class GSList extends com.sun.jna.Structure {
    public volatile Pointer data;
    public volatile Pointer _next;

    public GSList() {
      clear();
    }

    public GSList(Pointer ptr) {
      useMemory(ptr);
      read();
    }

    public static GSList valueOf(Pointer ptr) {
      return ptr != null ? new GSList(ptr) : null;
    }

    public GSList next() {
      return valueOf(_next);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"data", "_next"});
    }
  }

  String g_getenv(String variable);

  boolean g_setenv(String variable, String value, boolean overwrite);

  void g_unsetenv(String variable);
}
