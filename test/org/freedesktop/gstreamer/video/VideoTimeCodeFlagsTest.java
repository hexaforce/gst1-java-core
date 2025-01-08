package org.freedesktop.gstreamer.video;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class VideoTimeCodeFlagsTest {
  private final VideoTimeCodeFlags flags;
  private final int intValue;

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {{VideoTimeCodeFlags.GST_VIDEO_TIME_CODE_FLAGS_DROP_FRAME, 1}, {VideoTimeCodeFlags.GST_VIDEO_TIME_CODE_FLAGS_INTERLACED, 2}});
  }

  public VideoTimeCodeFlagsTest(VideoTimeCodeFlags flags, int intValue) {
    this.flags = flags;
    this.intValue = intValue;
  }

  @Test
  public void testIntValue() {
    assertEquals(intValue, flags.intValue());
  }
}
