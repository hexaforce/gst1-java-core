package org.freedesktop.gstreamer;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InitTest {
  public InitTest() {}

  @Test
  public void testInit() {
    Version available = Gst.getVersion();
    Version notAvailable = Version.of(available.getMajor(), available.getMinor() + 2);
    try {
      Gst.init(notAvailable);
      assertTrue("Version check exception not thrown!", false);
    } catch (GstException ex) {
      System.out.println("Expected init failure");
      System.out.println(ex);
    }
    String[] args = Gst.init(available, "InitTest", "--gst-debug=GST_PLUGIN_LOADING:4");
    assertTrue(args.length == 0);
    assertTrue(Gst.testVersion(available.getMajor(), available.getMinor()));
    assertTrue(Gst.testVersion(available.getMajor(), available.getMinor() - 2));
    assertTrue(!Gst.testVersion(notAvailable.getMajor(), notAvailable.getMinor()));
    Gst.deinit();
  }

  @BeforeClass
  public static void setUpClass() throws Exception {}

  @AfterClass
  public static void tearDownClass() throws Exception {}

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}
}
