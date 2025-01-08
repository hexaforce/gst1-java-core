package org.freedesktop.gstreamer.video;

import static org.freedesktop.gstreamer.video.VideoTimeCodeFlags.GST_VIDEO_TIME_CODE_FLAGS_DROP_FRAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.SampleTester;
import org.freedesktop.gstreamer.util.TestAssumptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class VideoTimeCodeMetaTest {
  @BeforeClass
  public static void beforeClass() {
    Gst.init(Gst.getVersion());
  }

  @AfterClass
  public static void afterClass() {
    Gst.deinit();
  }

  @Test
  public void testVideoWithoutTimeCodeMeta() {
    TestAssumptions.requireGstVersion(1, 14);
    SampleTester.test(sample -> {
      Buffer buffer = sample.getBuffer();
      assertFalse("Default video not contains timecode metadata", buffer.hasMeta(VideoTimeCodeMeta.API));
    }, "videotestsrc do-timestamp=true ! appsink name=myappsink");
  }

  @Test
  public void testVideoTimeCodeMetaPal() {
    TestAssumptions.requireGstVersion(1, 10);
    TestAssumptions.requireElement("timecodestamper");
    SampleTester.test(
        sample
        -> {
          Buffer buffer = sample.getBuffer();
          if (Gst.testVersion(1, 14)) {
            assertTrue("Video should contains timecode meta", buffer.hasMeta(VideoTimeCodeMeta.API));
          }
          VideoTimeCodeMeta meta = buffer.getMeta(VideoTimeCodeMeta.API);
          assertNotNull(meta);
          VideoTimeCode timeCode = meta.getTimeCode();
          assertEquals(0, timeCode.getHours());
          assertEquals(0, timeCode.getMinutes());
          assertEquals(0, timeCode.getSeconds());
          assertEquals(0, timeCode.getFrames());
          VideoTimeCodeConfig timeCodeConfig = timeCode.getConfig();
          assertEquals(25, timeCodeConfig.getNumerator());
          assertEquals(1, timeCodeConfig.getDenominator());
          assertTrue(timeCodeConfig.getFlags().isEmpty());
        },
        "videotestsrc do-timestamp=true ! video/x-raw,framerate=25/1 ! timecodestamper !"
            + " videoconvert ! appsink name=myappsink");
  }

  @Test
  public void testVideoTimeCodeNTSCDrop() {
    TestAssumptions.requireGstVersion(1, 10);
    TestAssumptions.requireElement("timecodestamper");
    SampleTester.test(
        sample
        -> {
          Buffer buffer = sample.getBuffer();
          if (Gst.testVersion(1, 14)) {
            assertTrue("Video should contains timecode meta", buffer.hasMeta(VideoTimeCodeMeta.API));
          }
          VideoTimeCodeMeta meta = buffer.getMeta(VideoTimeCodeMeta.API);
          assertNotNull(meta);
          VideoTimeCode timeCode = meta.getTimeCode();
          assertEquals(0, timeCode.getHours());
          assertEquals(0, timeCode.getMinutes());
          assertEquals(0, timeCode.getSeconds());
          assertEquals(0, timeCode.getFrames());
          VideoTimeCodeConfig timeCodeConfig = timeCode.getConfig();
          assertEquals(30000, timeCodeConfig.getNumerator());
          assertEquals(1001, timeCodeConfig.getDenominator());
          assertTrue(timeCodeConfig.getFlags().contains(GST_VIDEO_TIME_CODE_FLAGS_DROP_FRAME));
        },
        "videotestsrc ! video/x-raw,framerate=30000/1001 ! timecodestamper drop-frame=true !"
            + " videoconvert ! appsink name=myappsink");
  }

  @Test
  public void testVideoTimeCodeNTSCDropFrame() {
    TestAssumptions.requireGstVersion(1, 10);
    TestAssumptions.requireElement("timecodestamper");
    SampleTester.test(
        sample
        -> {
          Buffer buffer = sample.getBuffer();
          if (Gst.testVersion(1, 14)) {
            assertTrue("Video should contains timecode meta", buffer.hasMeta(VideoTimeCodeMeta.API));
          }
          VideoTimeCodeMeta meta = buffer.getMeta(VideoTimeCodeMeta.API);
          assertNotNull(meta);
          VideoTimeCode timeCode = meta.getTimeCode();
          assertEquals(0, timeCode.getHours());
          assertEquals(0, timeCode.getMinutes());
          assertEquals(0, timeCode.getSeconds());
          assertEquals(29, timeCode.getFrames());
          VideoTimeCodeConfig timeCodeConfig = timeCode.getConfig();
          assertEquals(30000, timeCodeConfig.getNumerator());
          assertEquals(1001, timeCodeConfig.getDenominator());
          assertTrue(timeCodeConfig.getFlags().contains(GST_VIDEO_TIME_CODE_FLAGS_DROP_FRAME));
        },
        "videotestsrc ! video/x-raw,framerate=30000/1001 ! videoconvert ! timecodestamper"
            + " drop-frame=true ! videoconvert ! appsink name=myappsink",
        29);
  }

  @Test
  public void testVideoTimeCodeNTSCNonDrop() {
    TestAssumptions.requireGstVersion(1, 10);
    TestAssumptions.requireElement("timecodestamper");
    SampleTester.test(
        sample
        -> {
          Buffer buffer = sample.getBuffer();
          if (Gst.testVersion(1, 14)) {
            assertTrue("Video should contains timecode meta", buffer.hasMeta(VideoTimeCodeMeta.API));
          }
          VideoTimeCodeMeta meta = buffer.getMeta(VideoTimeCodeMeta.API);
          assertNotNull(meta);
          VideoTimeCode timeCode = meta.getTimeCode();
          assertEquals(0, timeCode.getHours());
          assertEquals(0, timeCode.getMinutes());
          assertEquals(0, timeCode.getSeconds());
          assertEquals(0, timeCode.getFrames());
          VideoTimeCodeConfig timeCodeConfig = timeCode.getConfig();
          assertEquals(30, timeCodeConfig.getNumerator());
          assertEquals(1, timeCodeConfig.getDenominator());
          assertTrue(timeCodeConfig.getFlags().isEmpty());
        },
        "videotestsrc ! video/x-raw,framerate=30/1 ! timecodestamper ! videoconvert ! appsink"
            + " name=myappsink");
  }
}
