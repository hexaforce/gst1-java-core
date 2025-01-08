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

import org.junit.*;

public class BufferFieldsTest {
  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("BufferFieldsTest");
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  private Buffer buf;

  @Before
  public void setUp() {
    buf = new Buffer(12);
  }

  @Test
  public void setPTS() {
    buf.setPresentationTimestamp(ClockTime.fromMicros(5004003));
    long val = buf.getPresentationTimestamp();
    assertEquals(5004003, ClockTime.toMicros(val));
  }

  @Test
  public void setDTS() {
    buf.setDecodeTimestamp(ClockTime.fromMicros(9001004));
    long val = buf.getDecodeTimestamp();
    assertEquals(9001004, ClockTime.toMicros(val));
  }

  @Test
  public void setDuration() {
    buf.setDuration(ClockTime.fromMicros(4006008));
    long val = buf.getDuration();
    assertEquals(4006008, ClockTime.toMicros(val));
  }

  @Test
  public void setOffset() {
    buf.setOffset(2009006);
    long val = buf.getOffset();
    assertEquals(2009006, val);
  }

  @Test
  public void setOffsetEnd() {
    buf.setOffsetEnd(7005003);
    long val = buf.getOffsetEnd();
    assertEquals(7005003, val);
  }

  @Test
  public void setFlags() {}
}
