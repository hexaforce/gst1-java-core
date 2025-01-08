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

import static org.junit.Assert.assertEquals;

import org.freedesktop.gstreamer.glib.GLib;
import org.junit.Test;

public class GLibTest {
  @Test
  public void getEnv() {
    String user = GLib.getEnv("USER");
    String pwd = GLib.getEnv("PWD");
    System.out.println("user: " + user);
    System.out.println("path: " + pwd);
  }

  @Test
  public void setUnsetEnv() {
    GLib.setEnv("TESTVAR", "foo", true);
    assertEquals("could not set TESTVAR!", GLib.getEnv("TESTVAR"), "foo");
    GLib.unsetEnv("TESTVAR");
    assertEquals("could not unset TESTVAR!", GLib.getEnv("TESTVAR"), null);
  }
}
