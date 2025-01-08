package org.freedesktop.gstreamer.glib;

import java.util.EnumSet;
import java.util.Set;

public interface NativeFlags<T extends Enum<T>> extends NativeEnum<T> {
  public static <FLAG extends Enum<FLAG> & NativeFlags<FLAG>> int toInt(Set<FLAG> flags) {
    int ret = 0;
    for (FLAG flag : flags) {
      ret |= flag.intValue();
    }
    return ret;
  }

  public static <FLAG extends Enum<FLAG> & NativeFlags<FLAG>> int toInt(EnumSet<FLAG> flags) {
    int ret = 0;
    for (FLAG flag : flags) {
      ret |= flag.intValue();
    }
    return ret;
  }

  public static <FLAG extends Enum<FLAG> & NativeFlags<FLAG>> EnumSet<FLAG> fromInt(Class<FLAG> type, int val) {
    EnumSet<FLAG> set = EnumSet.allOf(type);
    set.removeIf(f -> ((val & f.intValue()) == 0));
    return set;
  }
}
