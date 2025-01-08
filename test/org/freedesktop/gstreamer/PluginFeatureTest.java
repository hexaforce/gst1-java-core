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

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PluginFeatureTest {
  private static PluginFeature decodebinFeature;

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("PluginTest", new String[] {});
    decodebinFeature = Registry.get().lookupFeature("decodebin");
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  @Test
  public void testToString() {
    assertEquals("decodebin", decodebinFeature.toString());
  }

  @Test
  public void testGetName() {
    assertEquals("decodebin", decodebinFeature.getName());
  }

  @Test
  public void testGetRank() {
    assertEquals(0, decodebinFeature.getRank());
  }

  @Test
  public void testCheckVersion() {
    assertTrue(decodebinFeature.checkVersion(0, 0, 1));
  }

  @Test
  public void testGetPluginName() {
    assertEquals("playback", decodebinFeature.getPluginName());
  }

  public void testGetPlugin() {
    Plugin plugin = decodebinFeature.getPlugin();
    assertNotNull(plugin);
    assertEquals("playback", plugin.getName());
  }
}
