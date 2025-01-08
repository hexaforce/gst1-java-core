package org.freedesktop.gstreamer.video;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.freedesktop.gstreamer.lowlevel.GstVideoAPI;
import org.junit.Before;
import org.junit.Test;

public class VideoTimeCodeTest {
  private GstVideoAPI.GstVideoTimeCodeStruct timeCodeStruct;
  private GstVideoAPI.GstVideoTimeCodeConfigStruct.ByValue configStruct;
  private VideoTimeCode timeCode;

  @Before
  public void setUp() {
    timeCodeStruct = new GstVideoAPI.GstVideoTimeCodeStruct();
    configStruct = new GstVideoAPI.GstVideoTimeCodeConfigStruct.ByValue();
    timeCodeStruct.hours = 1;
    timeCodeStruct.minutes = 2;
    timeCodeStruct.seconds = 3;
    timeCodeStruct.frames = 4;
    timeCodeStruct.field_count = 55;
    timeCodeStruct.config = configStruct;
    timeCodeStruct.write();
    timeCode = new VideoTimeCode(timeCodeStruct);
  }

  @Test
  public void testGetTCConfig() {
    assertNotNull(timeCode.getConfig());
  }

  @Test
  public void testGetHours() {
    assertEquals(1, timeCode.getHours());
  }

  @Test
  public void testGetMinutes() {
    assertEquals(2, timeCode.getMinutes());
  }

  @Test
  public void testGetSeconds() {
    assertEquals(3, timeCode.getSeconds());
  }

  @Test
  public void testGetFrames() {
    assertEquals(4, timeCode.getFrames());
  }
}
