package org.freedesktop.gstreamer.glib;

public interface NativeEnum<T extends Enum<T>> {
  public int intValue();

  public static <T extends Enum<T> & NativeEnum<T>> T fromInt(Class<T> type, int intValue) {
    for (T value : type.getEnumConstants()) {
      if (value.intValue() == intValue) {
        return value;
      }
    }
    throw new IllegalArgumentException("Value " + intValue + " is unacceptable for " + type.getSimpleName() + " enum");
  }

  public static <T extends Enum<T> & NativeEnum<T>> T fromInt(Class<T> type, T defValue, int intValue) {
    for (T value : type.getEnumConstants()) {
      if (value.intValue() == intValue) {
        return value;
      }
    }
    return defValue;
  }
}
