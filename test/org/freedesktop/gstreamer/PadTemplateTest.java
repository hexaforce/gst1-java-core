package org.freedesktop.gstreamer;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PadTemplateTest {
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
  public void padTemplate() throws Exception {
    Element src = ElementFactory.make("fakesrc", "src");
    Element sink = ElementFactory.make("fakesink", "sink");
    Pad srcPad = src.getStaticPad("src");
    Pad sinkPad = sink.getStaticPad("sink");
    PadTemplate template;
    template = srcPad.getTemplate();
    assertEquals("wrong name!", template.getTemplateName(), "src");
    assertEquals("wrong direction!", template.getDirection(), PadDirection.SRC);
    assertEquals("wrong presence!", template.getPresence(), PadPresence.ALWAYS);
    template = sinkPad.getTemplate();
    assertEquals("wrong name!", template.getTemplateName(), "sink");
    assertEquals("wrong direction!", template.getDirection(), PadDirection.SINK);
    assertEquals("wrong presence!", template.getPresence(), PadPresence.ALWAYS);
  }
}
