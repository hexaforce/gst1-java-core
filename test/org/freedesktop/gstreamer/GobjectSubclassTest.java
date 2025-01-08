package org.freedesktop.gstreamer;

import static org.freedesktop.gstreamer.lowlevel.GObjectAPI.GOBJECT_API;
import static org.freedesktop.gstreamer.lowlevel.GstPadTemplateAPI.GSTPADTEMPLATE_API;
import static org.junit.Assert.assertEquals;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.lowlevel.BaseSrcAPI;
import org.freedesktop.gstreamer.lowlevel.GObjectAPI;
import org.freedesktop.gstreamer.lowlevel.GObjectAPI.GClassInitFunc;
import org.freedesktop.gstreamer.lowlevel.GObjectAPI.GInstanceInitFunc;
import org.freedesktop.gstreamer.lowlevel.GObjectAPI.GTypeInstance;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GobjectSubclassTest {
  public GobjectSubclassTest() {}

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
  public void registerNewGObjectClass() throws Exception {
    final PadTemplate template = new PadTemplate("src", PadDirection.SRC, Caps.anyCaps());
    final boolean[] classInitCalled = {false};
    final GClassInitFunc classInit = new GClassInitFunc() {
      public void callback(Pointer g_class, Pointer class_data) {
        classInitCalled[0] = true;
      }
    };
    final GObjectAPI.GBaseInitFunc baseInit = new GObjectAPI.GBaseInitFunc() {
      public void callback(Pointer g_class) {
        GSTPADTEMPLATE_API.gst_element_class_add_pad_template(g_class, template);
      }
    };
    final boolean[] instanceInitCalled = {false};
    final GInstanceInitFunc instanceInit = new GInstanceInitFunc() {
      public void callback(GTypeInstance instance, Pointer g_class) {
        instanceInitCalled[0] = true;
      }
    };
    final String name = "NewTestClass";
    GObjectAPI.GTypeInfo info = new GObjectAPI.GTypeInfo();
    info.clear();
    info.class_init = classInit;
    info.instance_init = instanceInit;
    info.class_size = (short) new BaseSrcAPI.GstBaseSrcClass().size();
    info.instance_size = (short) new BaseSrcAPI.GstBaseSrcStruct().size();
    info.class_size = 1024;
    info.base_init = baseInit;
    info.instance_size = 1024;
    GType type = GOBJECT_API.g_type_register_static(BaseSrcAPI.BASESRC_API.gst_base_src_get_type(), name, info, 0);
    System.out.println("New type=" + type);
    assertEquals("Name incorrect", name, GOBJECT_API.g_type_name(type));
    assertEquals("Cannot locate type by name", type, GOBJECT_API.g_type_from_name(name));
    GOBJECT_API.g_object_new(type, new Object[0]);
  }
}
