package org.freedesktop.gstreamer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GhostPadTest {
  public GhostPadTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("GhostPadTest", new String[] {});
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
  public void newGhostPad() {
    Element fakesink = ElementFactory.make("fakesink", "fs");
    @SuppressWarnings("unused") GhostPad gpad = new GhostPad("ghostsink", fakesink.getStaticPad("sink"));
  }
}
