package org.freedesktop.gstreamer;

import static org.junit.Assert.assertEquals;

import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GObjectPtr;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.freedesktop.gstreamer.lowlevel.GstTypes;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GstTypesTest {
  public GstTypesTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("GstTypesTest", new String[] {});
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
  public void registeredClassTest() {
    GType elementType = GType.valueOf(Element.GTYPE_NAME);
    assertEquals(Element.class, GstTypes.classFor(elementType));
    assertEquals(elementType, GstTypes.typeFor(Element.class));
  }

  @Test
  public void unregisteredClassTest() {
    GType elementType = GType.valueOf(Element.GTYPE_NAME);
    Element anElement = ElementFactory.make("avidemux", "avidemux");
    assertEquals(Element.class, GstTypes.classFor(Natives.getPointer(anElement).as(GObjectPtr.class, GObjectPtr::new).getGType()));
    assertEquals(elementType, GstTypes.typeFor(Element.class));
  }
}
