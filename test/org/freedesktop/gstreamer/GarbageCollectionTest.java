package org.freedesktop.gstreamer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GarbageCollectionTest {
  public GarbageCollectionTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("test", new String[] {});
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testElement() throws Exception {
    Element e = ElementFactory.make("fakesrc", "test element");
    GCTracker tracker = new GCTracker(e);
    e = null;
    assertTrue("Element not garbage collected", tracker.waitGC());
    assertTrue("GObject not destroyed", tracker.waitDestroyed());
  }

  @Test
  public void testBin() throws Exception {
    Bin bin = new Bin("test");
    Element e1 = ElementFactory.make("fakesrc", "source");
    Element e2 = ElementFactory.make("fakesink", "sink");
    bin.addMany(e1, e2);
    assertEquals("source not returned", e1, bin.getElementByName("source"));
    assertEquals("sink not returned", e2, bin.getElementByName("sink"));
    GCTracker binTracker = new GCTracker(bin);
    bin = null;
    assertTrue("Bin not garbage collected", binTracker.waitGC());
    assertTrue("Bin not destroyed", binTracker.waitDestroyed());
    GCTracker e1Tracker = new GCTracker(e1);
    GCTracker e2Tracker = new GCTracker(e2);
    e1 = null;
    e2 = null;
    assertTrue("First Element not garbage collected", e1Tracker.waitGC());
    assertTrue("First Element not destroyed", e1Tracker.waitDestroyed());
    assertTrue("Second Element not garbage collected", e2Tracker.waitGC());
    assertTrue("Second Element not destroyed", e2Tracker.waitDestroyed());
  }

  @Test
  public void testBinParsed() throws Exception {
    Bin bin = Gst.parseBinFromDescription("fakesrc name=source ! fakesink name=sink", false);
    int binRefCount = bin.getRefCount();
    List<Element> children = bin.getElements();
    assertEquals("Iteration increased Bin refcount", binRefCount, bin.getRefCount());
    assertEquals("Wrong number of child elements", 2, children.size());
    Element e1 = children.get(0);
    Element e2 = children.get(1);
    GCTracker binTracker = new GCTracker(bin);
    bin = null;
    assertTrue("Bin not garbage collected", binTracker.waitGC());
    assertTrue("Bin not destroyed", binTracker.waitDestroyed());
    GCTracker e1Tracker = new GCTracker(e1);
    GCTracker e2Tracker = new GCTracker(e2);
    children = null;
    e1 = null;
    e2 = null;
    assertTrue("First Element not garbage collected", e1Tracker.waitGC());
    assertTrue("First Element not destroyed", e1Tracker.waitDestroyed());
    assertTrue("Second Element not garbage collected", e2Tracker.waitGC());
    assertTrue("Second Element not destroyed", e2Tracker.waitDestroyed());
  }

  @Test
  public void testBinRetrieval() throws Exception {
    Bin bin = new Bin("test");
    Element e1 = ElementFactory.make("fakesrc", "source");
    Element e2 = ElementFactory.make("fakesink", "sink");
    bin.addMany(e1, e2);
    int id1 = System.identityHashCode(e1);
    int id2 = System.identityHashCode(e2);
    e1 = null;
    e2 = null;
    System.gc();
    Thread.sleep(10);
    assertEquals("source ID does not match", id1, System.identityHashCode(bin.getElementByName("source")));
    assertEquals("sink ID does not match", id2, System.identityHashCode(bin.getElementByName("sink")));
  }

  @Test
  public void pipeline() {
    Pipeline pipe = new Pipeline("test");
    GCTracker pipeTracker = new GCTracker(pipe);
    pipe = null;
    assertTrue("Pipe not garbage collected", pipeTracker.waitGC());
    System.out.println("checking if pipeline is destroyed");
    assertTrue("Pipe not destroyed", pipeTracker.waitDestroyed());
  }

  @Test
  public void pipelineBus() {
    Pipeline pipe = new Pipeline("test");
    Bus bus = pipe.getBus();
    GCTracker busTracker = new GCTracker(bus);
    GCTracker pipeTracker = new GCTracker(pipe);
    pipe = null;
    bus = null;
    assertTrue("Bus not garbage collected", busTracker.waitGC());
    assertTrue("Bus not destroyed", busTracker.waitDestroyed());
    assertTrue("Pipe not garbage collected", pipeTracker.waitGC());
    assertTrue("Pipe not destroyed", pipeTracker.waitDestroyed());
  }

  @Test
  public void busWithListeners() {
    Pipeline pipe = new Pipeline("test");
    Bus bus = pipe.getBus();
    bus.connect(new Bus.EOS() {
      public void endOfStream(GstObject source) {}
    });
    GCTracker busTracker = new GCTracker(bus);
    GCTracker pipeTracker = new GCTracker(pipe);
    bus = null;
    pipe = null;
    assertTrue("Bus not garbage collected", busTracker.waitGC());
    assertTrue("Bus not destroyed", busTracker.waitDestroyed());
    assertTrue("Pipe not garbage collected", pipeTracker.waitGC());
    assertTrue("Pipe not destroyed", pipeTracker.waitDestroyed());
  }
}
