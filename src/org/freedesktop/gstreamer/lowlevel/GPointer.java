package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import java.util.function.Function;

public class GPointer extends PointerType {
  public GPointer() {}

  public GPointer(Pointer ptr) {
    super(ptr);
  }

  public <T extends GPointer> T as(Class<T> cls, Function<Pointer, T> converter) {
    if (cls.isInstance(this)) {
      return cls.cast(this);
    } else {
      return converter.apply(this.getPointer());
    }
  }
}
