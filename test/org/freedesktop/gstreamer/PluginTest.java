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

public class PluginTest {
  private static Plugin playbackPlugin;

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("PluginTest", new String[] {});
    playbackPlugin = Plugin.loadByName("playback");
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  public PluginTest() {}

  @Test
  public void testLoad_String() {
    assertNotNull(playbackPlugin);
  }

  @Test
  public void testGetName() {
    assertTrue(playbackPlugin.getName().equals("playback"));
  }

  @Test
  public void testGetDescription() {
    assertTrue(playbackPlugin.getDescription().equals("various playback elements"));
  }

  @Test
  public void testGetFilename() {
    assertTrue(playbackPlugin.getFilename().contains("gstplayback"));
  }

  @Test
  public void testGetVersion() {
    assertTrue(playbackPlugin.getVersion().matches("^(?:\\d+\\.)*\\d+$"));
  }

  @Test
  public void testGetLicense() {
    assertTrue(playbackPlugin.getLicense().equals("LGPL"));
  }

  @Test
  public void testGetSource() {
    assertTrue(playbackPlugin.getSource().equals("gst-plugins-base"));
  }

  @Test
  public void testGetPackage() {
    String pkg = playbackPlugin.getPackage();
    assertTrue(pkg.contains("GStreamer Base") || pkg.contains("Gentoo GStreamer"));
  }

  @Test
  public void testGetOrigin() {
    assertTrue(playbackPlugin.getOrigin().length() > 0);
  }

  @Test
  public void testGetReleaseDateString() {
    assertTrue(playbackPlugin.getReleaseDateString().matches(".*\\d{4}-\\d{2}-\\d{2}.*"));
  }

  public void testIsLoaded() {
    assertTrue(playbackPlugin.isLoaded());
  }
}
