package org.freedesktop.gstreamer;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Set;
import org.freedesktop.gstreamer.glib.NativeFlags;
import org.freedesktop.gstreamer.lowlevel.GstPadAPI;
import org.junit.Test;

public class PadProbeTypeTest {
  @Test
  public void testCombinations() throws Exception {
    for (Field field : PadProbeType.class.getFields()) {
      if (Set.class.isAssignableFrom(field.getType())) {
        Set<PadProbeType> flags = (Set<PadProbeType>) field.get(null);
        Field nativeField = GstPadAPI.class.getField("GST_PAD_PROBE_TYPE_" + field.getName());
        assertEquals(NativeFlags.toInt(flags), nativeField.get(null));
      }
    }
  }
}
