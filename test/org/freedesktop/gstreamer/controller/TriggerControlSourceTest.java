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

package org.freedesktop.gstreamer.controller;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.freedesktop.gstreamer.ClockTime;
import org.freedesktop.gstreamer.ControlBinding;
import org.freedesktop.gstreamer.ControlSource;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.GCTracker;
import org.freedesktop.gstreamer.Gst;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TriggerControlSourceTest {
  public TriggerControlSourceTest() {}

  @BeforeClass
  public static void setUpClass() {
    Gst.init("InterpolationControlSourceTest");
  }

  @AfterClass
  public static void tearDownClass() {}

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  @Test
  public void testTolerance() {
    List<ControlSource.TimedValue> timedValues = Stream.of(new ControlSource.TimedValue(ClockTime.fromSeconds(0), 0.5), new ControlSource.TimedValue(ClockTime.fromSeconds(2), 1.0)).collect(Collectors.toList());
    TriggerControlSource controller = new TriggerControlSource();
    controller.setFromList(timedValues);
    Element volume = ElementFactory.make("volume", "volume");
    volume.addControlBinding(DirectControlBinding.create(volume, "volume", controller));
    volume.syncValues(0);
    assertEquals(5, ((Double) volume.get("volume")), 0.001);
    volume.set("volume", 0);
    volume.syncValues(ClockTime.fromSeconds(1));
    assertEquals(0, ((Double) volume.get("volume")), 0.001);
    volume.syncValues(ClockTime.fromSeconds(2));
    assertEquals(10, ((Double) volume.get("volume")), 0.001);
    controller.setTolerance(ClockTime.fromMillis(500));
    volume.syncValues(ClockTime.fromMillis(450));
    assertEquals(5, ((Double) volume.get("volume")), 0.001);
    volume.set("volume", 0);
    volume.syncValues(ClockTime.fromMillis(550));
    assertEquals(0, ((Double) volume.get("volume")), 0.001);
    volume.syncValues(ClockTime.fromMillis(1650));
    assertEquals(10, ((Double) volume.get("volume")), 0.001);
  }

  @Test
  public void testGC() {
    TriggerControlSource controller = new TriggerControlSource();
    Element volume = ElementFactory.make("volume", "volume");
    ControlBinding binding = DirectControlBinding.create(volume, "volume", controller);
    volume.addControlBinding(binding);
    GCTracker tracker = new GCTracker(controller);
    controller = null;
    binding = null;
    volume = null;
    assertTrue("Controller not garbage collected", tracker.waitGC());
    assertTrue("Controller not destroyed", tracker.waitDestroyed());
  }
}
