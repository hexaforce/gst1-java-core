package org.freedesktop.gstreamer.video;

import static org.junit.Assert.assertEquals;

import org.freedesktop.gstreamer.glib.NativeFlags;
import org.freedesktop.gstreamer.lowlevel.GstVideoAPI;
import org.junit.Before;
import org.junit.Test;

public class VideoTimeCodeConfigTest {
  private GstVideoAPI.GstVideoTimeCodeConfigStruct origStruct;
  private VideoTimeCodeConfig codeConfig;

  @Before
  public void setUp() {
    origStruct = new GstVideoAPI.GstVideoTimeCodeConfigStruct();
    origStruct.fps_d = 25;
    origStruct.fps_n = 1;
    origStruct.flags = VideoTimeCodeFlags.GST_VIDEO_TIME_CODE_FLAGS_DROP_FRAME.intValue();
    origStruct.write();
    codeConfig = new VideoTimeCodeConfig(origStruct);
  }

  @Test
  public void testGetTimeCodeFlags() {
    assertEquals(origStruct.flags, NativeFlags.toInt(codeConfig.getFlags()));
  }

  @Test
  public void testGetFramerateNumerator() {
    assertEquals(origStruct.fps_n, codeConfig.getNumerator());
  }

  @Test
  public void testGetFramerateDenominator() {
    assertEquals(origStruct.fps_d, codeConfig.getDenominator());
  }
}
