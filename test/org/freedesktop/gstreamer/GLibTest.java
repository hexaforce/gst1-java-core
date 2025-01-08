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
