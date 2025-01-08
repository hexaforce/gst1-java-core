package org.freedesktop.gstreamer.glib;

import static org.freedesktop.gstreamer.glib.Natives.registration;

import java.util.stream.Stream;
import org.freedesktop.gstreamer.lowlevel.GlibAPI;

public class GLib {
  public static boolean setEnv(String variable, final String value, boolean overwrite) {
    return GlibAPI.GLIB_API.g_setenv(variable, value, overwrite);
  }

  public static String getEnv(String variable) {
    return GlibAPI.GLIB_API.g_getenv(variable);
  }

  public static void unsetEnv(String variable) {
    GlibAPI.GLIB_API.g_unsetenv(variable);
  }

  public static class Types implements NativeObject.TypeProvider {
    @Override
    public Stream<NativeObject.TypeRegistration<?>> types() {
      return Stream.of(registration(GDate.class, GDate.GTYPE_NAME, GDate::new), registration(GInetAddress.class, GInetAddress.GTYPE_NAME, GInetAddress::new), registration(GSocket.class, GSocket.GTYPE_NAME, GSocket::new), registration(GSocketAddress.class, GSocketAddress.GTYPE_NAME, GSocketAddress::new), registration(GInetSocketAddress.class, GInetSocketAddress.GTYPE_NAME, GInetSocketAddress::new));
    }
  }
}
