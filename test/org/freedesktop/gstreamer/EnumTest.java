package org.freedesktop.gstreamer;

import static org.junit.Assert.assertEquals;

import org.freedesktop.gstreamer.lowlevel.EnumMapper;
import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EnumTest {
  public EnumTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("EnumTest", new String[] {});
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  private static enum TestEnum {
    FOO,
    @DefaultEnumValue BAR;
  }

  @Test
  public void valueOfInt() {
    TestEnum e = EnumMapper.getInstance().valueOf(0xdeadbeef, TestEnum.class);
    assertEquals("Wrong value returned for the default", TestEnum.BAR, e);
  }
}
