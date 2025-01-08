package org.freedesktop.gstreamer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.freedesktop.gstreamer.glib.GError;
import org.freedesktop.gstreamer.glib.GObject;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GObjectAPI.GObjectStruct;
import org.freedesktop.gstreamer.lowlevel.GObjectPtr;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PipelineTest {
  public PipelineTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("PipelineTest", new String[] {});
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  public boolean waitRefCnt(GObjectStruct struct, int refcnt) throws InterruptedException {
    System.gc();
    struct.read();
    for (int i = 0; struct.ref_count != refcnt && i < 20; ++i) {
      Thread.sleep(10);
      System.gc();
      struct.read();
    }
    return struct.ref_count == refcnt;
  }

  @Test
  public void testPipelineGC() throws Exception {
    Pipeline p = new Pipeline("test pipeline");
    int refcnt = new GObjectStruct((GObjectPtr) Natives.getPointer(p)).ref_count;
    assertEquals("Refcount should be 1", refcnt, 1);
    WeakReference<GObject> pref = new WeakReference<GObject>(p);
    p = null;
    assertTrue("pipe not disposed", GCTracker.waitGC(pref));
  }

  @Test
  public void testBusGC() throws Exception {
    Pipeline pipe = new Pipeline("test playbin");
    pipe.play();
    Bus bus = pipe.getBus();
    GObjectStruct struct = new GObjectStruct((GObjectPtr) Natives.getPointer(bus));
    int refcnt = struct.ref_count;
    assertTrue(refcnt > 1);
    Bus bus2 = pipe.getBus();
    assertTrue("Did not get same Bus object", bus == bus2);
    struct.read();
    assertEquals("ref_count not equal", refcnt, struct.ref_count);
    bus2 = null;
    WeakReference<Bus> bref = new WeakReference<Bus>(bus);
    bus = null;
    assertFalse("bus disposed prematurely", GCTracker.waitGC(bref));
    assertFalse("ref_count decremented prematurely", waitRefCnt(struct, refcnt - 1));
    WeakReference<GObject> pref = new WeakReference<GObject>(pipe);
    pipe.stop();
    pipe = null;
    assertTrue("pipe not disposed", GCTracker.waitGC(pref));
    struct.read();
    System.out.println("bus ref_count=" + struct.ref_count);
    bus = null;
    assertTrue("bus not disposed " + struct.ref_count, GCTracker.waitGC(bref));
  }

  @Test
  public void testParseLaunch() {
    ArrayList<GError> errors = new ArrayList<GError>();
    Pipeline pipeline = (Pipeline) Gst.parseLaunch("fakesrc ! fakesink", errors);
    assertNotNull("Pipeline not created", pipeline);
    assertEquals("parseLaunch with error!", errors.size(), 0);
  }

  @Test
  public void testParseLaunchSingleElement() {
    ArrayList<GError> errors = new ArrayList<GError>();
    Element element = Gst.parseLaunch("fakesink", errors);
    assertNotNull("Element not created", element);
    assertFalse("Single element returned in Pipeline", element instanceof Pipeline);
    assertEquals("parseLaunch with error!", errors.size(), 0);
  }

  @Test
  public void testParseLaunchElementCount() {
    ArrayList<GError> errors = new ArrayList<GError>();
    Pipeline pipeline = (Pipeline) Gst.parseLaunch("fakesrc ! fakesink", errors);
    assertEquals("Number of elements in pipeline incorrect", 2, pipeline.getElements().size());
    assertEquals("parseLaunch with error!", errors.size(), 0);
  }

  @Test
  public void testParseLaunchSrcElement() {
    ArrayList<GError> errors = new ArrayList<GError>();
    Pipeline pipeline = (Pipeline) Gst.parseLaunch("fakesrc ! fakesink", errors);
    assertEquals("First element not a fakesrc", "fakesrc", pipeline.getSources().get(0).getFactory().getName());
    assertEquals("parseLaunch with error!", errors.size(), 0);
  }

  @Test
  public void testParseLaunchSinkElement() {
    ArrayList<GError> errors = new ArrayList<GError>();
    Pipeline pipeline = (Pipeline) Gst.parseLaunch("fakesrc ! fakesink", errors);
    assertEquals("First element not a fakesink", "fakesink", pipeline.getSinks().get(0).getFactory().getName());
    assertEquals("parseLaunch with error!", errors.size(), 0);
  }

  @Test
  public void testParseLaunchStringArr() {
    ArrayList<GError> errors = new ArrayList<GError>();
    Pipeline pipeline = (Pipeline) Gst.parseLaunch(new String[] {"fakesrc", "fakesink"}, errors);
    assertNotNull("Pipeline not created", pipeline);
    assertEquals("parseLaunch with error!", errors.size(), 0);
  }

  @Test
  public void testParseLaunchStringArrElementCount() {
    ArrayList<GError> errors = new ArrayList<GError>();
    Pipeline pipeline = (Pipeline) Gst.parseLaunch(new String[] {"fakesrc", "fakesink"}, errors);
    assertEquals("Number of elements in pipeline incorrect", 2, pipeline.getElements().size());
    assertEquals("parseLaunch with error!", errors.size(), 0);
  }

  @Test
  public void testParseLaunchStringArrSrcElement() {
    ArrayList<GError> errors = new ArrayList<GError>();
    Pipeline pipeline = (Pipeline) Gst.parseLaunch(new String[] {"fakesrc", "fakesink"}, errors);
    assertEquals("First element not a fakesrc", "fakesrc", pipeline.getSources().get(0).getFactory().getName());
    assertEquals("parseLaunch with error!", errors.size(), 0);
  }

  @Test
  public void testParseLaunchStringArrSinkElement() {
    ArrayList<GError> errors = new ArrayList<GError>();
    Pipeline pipeline = (Pipeline) Gst.parseLaunch(new String[] {"fakesrc", "fakesink"}, errors);
    assertEquals("First element not a fakesink", "fakesink", pipeline.getSinks().get(0).getFactory().getName());
    assertEquals("parseLaunch with error!", errors.size(), 0);
  }
}
