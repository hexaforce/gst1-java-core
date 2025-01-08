package org.freedesktop.gstreamer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.freedesktop.gstreamer.message.Message;
import org.freedesktop.gstreamer.message.TagMessage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ElementTest {
  public ElementTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("ElementTest", new String[] {});
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  @Test
  public void getPads() {
    Element element = ElementFactory.make("fakesink", "fs");
    List<Pad> pads = element.getPads();
    assertTrue("no pads found", !pads.isEmpty());
  }

  @Test
  public void getSinkPads() {
    Element element = ElementFactory.make("fakesink", "fs");
    List<Pad> pads = element.getSinkPads();
    assertTrue("no pads found", !pads.isEmpty());
  }

  @Test
  public void getSrcPads() {
    Element element = ElementFactory.make("fakesrc", "fs");
    List<Pad> pads = element.getSrcPads();
    assertTrue("no pads found", !pads.isEmpty());
  }

  @Test
  public void setState() {
    Element element = ElementFactory.make("fakesrc", "fs");
    element.play();
    element.stop();
  }

  @Test
  public void getState() {
    Element element = ElementFactory.make("fakesrc", "fs");
    element.play();
    State state = element.getState(-1);
    assertEquals("Element state not set correctly", State.PLAYING, state);
    element.stop();
  }

  @Test
  public void postMessage() {
    final TestPipe pipe = new TestPipe();
    final AtomicBoolean signalFired = new AtomicBoolean(false);
    final Message message = new TagMessage(pipe.src, new TagList());
    pipe.getBus().connect(new Bus.MESSAGE() {
      public void busMessage(Bus bus, Message msg) {
        if (msg.equals(message)) {
          signalFired.set(true);
          pipe.quit();
        }
      }
    });
    pipe.sink.postMessage(message);
    pipe.run();
    assertTrue("Message not posted", signalFired.get());
  }

  @Test
  public void testContext() {
    Element element = ElementFactory.make("fakesrc", "fs");
    Assert.assertEquals(1, element.getRefCount());
    Context context = new Context("test");
    Assert.assertEquals(1, context.getRefCount());
    element.setContext(context);
    Assert.assertEquals(2, context.getRefCount());
    Context anotherContext = element.getContext("test");
    Assert.assertEquals(2, anotherContext.getRefCount());
    Assert.assertNotNull(anotherContext);
    Assert.assertEquals(context.getContextType(), anotherContext.getContextType());
    Assert.assertNull(element.getContext("test-something-else"));
    element.dispose();
    Assert.assertEquals(0, element.getRefCount());
    Assert.assertEquals(1, context.getRefCount());
    Assert.assertEquals(1, anotherContext.getRefCount());
  }
}
