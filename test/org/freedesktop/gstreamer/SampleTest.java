package org.freedesktop.gstreamer;

import static org.junit.Assert.*;

import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.util.TestAssumptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SampleTest {
  public SampleTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init(Gst.getVersion(), "SampleTest");
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  @Test
  public void testGetCaps() {
    SampleTester.test((Sample sample) -> {
      Caps caps = sample.getCaps();
      Structure struct = caps.getStructure(0);
      String name = struct.getName();
      assertEquals("video/x-raw", name);
    });
  }

  @Test
  public void testGetBuffer() {
    SampleTester.test((Sample sample) -> {
      Buffer buffer = sample.getBuffer();
      assertEquals(1, buffer.getMemoryCount());
    });
  }

  @Test
  public void testSetBuffer() {
    TestAssumptions.requireGstVersion(1, 16);
    SampleTester.test((Sample sample) -> {
      Buffer buffer = sample.getBuffer();
      int refCount = buffer.getRefCount();
      assertEquals(2, sample.getRefCount());
      Natives.unref(sample);
      sample.setBuffer(null);
      Natives.ref(sample);
      assertEquals(2, sample.getRefCount());
      assertEquals(refCount - 1, buffer.getRefCount());
    });
  }

  @Test
  public void testSampleTester() {
    try {
      SampleTester.test(sample -> { throw new IllegalStateException(); });
    } catch (Throwable t) {
      assertTrue(t instanceof AssertionError);
      assertTrue(t.getCause() instanceof IllegalStateException);
      return;
    }
    fail("No exception thrown");
  }
}
