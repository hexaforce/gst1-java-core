package org.freedesktop.gstreamer;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClockTest {
  @BeforeClass
  public static void beforeClass() {
    Gst.init(Gst.getVersion());
  }

  @AfterClass
  public static void afterClass() {
    Gst.deinit();
  }

  @Test
  public void calibrationTest() {
    Pipeline pipe = (Pipeline) Gst.parseLaunch("autovideosrc ! autovideosink");
    Clock clock = pipe.getClock();
    Clock.Calibration cal1 = clock.getCalibration();
    System.out.println(cal1);
    assertEquals(0, cal1.internal());
    assertEquals(0, cal1.external());
    assertEquals(1, cal1.rateNum());
    assertEquals(1, cal1.rateDenom());
    clock.setCalibration(-100, 1000, 8, 5);
    Clock.Calibration cal2 = clock.getCalibration();
    System.out.println(cal2);
    assertEquals(-100, cal2.internal());
    assertEquals(1000, cal2.external());
    assertEquals(8, cal2.rateNum());
    assertEquals(5, cal2.rateDenom());
    Clock.Calibration cal3 = clock.getCalibration();
    assertEquals(cal2, cal3);
    assertEquals(cal2.hashCode(), cal3.hashCode());
    assertNotEquals(cal1, cal3);
  }
}
