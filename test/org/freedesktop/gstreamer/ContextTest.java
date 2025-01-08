/*
 * This file is part of gstreamer-java.
 * 
 * This code is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License version 3 only, as published by the Free Software Foundation.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License version 3 for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3 along with this work.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstContextAPI;
import org.freedesktop.gstreamer.lowlevel.GstContextPtr;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContextTest {
  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("test");
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  @Test
  public void testConstruction() {
    GstContextAPI contextApi = GstContextAPI.GSTCONTEXT_API;
    String contextType = "whatever";
    try (Context context = new Context(contextType)) {
      GstContextPtr gstContextPtr = Natives.getPointer(context).as(GstContextPtr.class, GstContextPtr::new);
      Assert.assertEquals(contextType, context.getContextType());
      Assert.assertTrue(contextApi.gst_context_has_context_type(gstContextPtr, contextType));
      Assert.assertFalse(contextApi.gst_context_has_context_type(gstContextPtr, contextType + ".something-else"));
      Assert.assertTrue(contextApi.gst_context_is_persistent(gstContextPtr));
      Assert.assertNotNull(context.getWritableStructure());
    }
  }
}
