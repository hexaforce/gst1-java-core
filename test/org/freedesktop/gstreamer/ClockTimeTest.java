package org.freedesktop.gstreamer;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClockTimeTest {
  public ClockTimeTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {}

  @AfterClass
  public static void tearDownClass() throws Exception {}

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  @Test
  public void toSeconds() {
    final long TIME = TimeUnit.SECONDS.toNanos(0xdeadbeef);
    assertEquals("toSeconds returned incorrect value", TimeUnit.NANOSECONDS.toSeconds(TIME), ClockTime.toSeconds(TIME));
  }

  @Test
  public void toMillis() {
    final long TIME = TimeUnit.SECONDS.toNanos(0xdeadbeef);
    assertEquals("toMillis returned incorrect value", TimeUnit.NANOSECONDS.toMillis(TIME), ClockTime.toMillis(TIME));
  }

  @Test
  public void toMicros() {
    final long TIME = TimeUnit.SECONDS.toNanos(0xdeadbeef);
    assertEquals("toMillis returned incorrect value", TimeUnit.NANOSECONDS.toMicros(TIME), ClockTime.toMicros(TIME));
  }

  @Test
  public void toStringRepresentation() {
    long hours = 3;
    long minutes = 27;
    long seconds = 13;
    long time = TimeUnit.HOURS.toNanos(hours) + TimeUnit.MINUTES.toNanos(minutes) + TimeUnit.SECONDS.toNanos(seconds);
    assertEquals("ClockTime.toString() incorrect", "03:27:13", ClockTime.toString(time));
  }
}
