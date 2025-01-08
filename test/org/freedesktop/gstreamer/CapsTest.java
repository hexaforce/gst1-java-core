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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CapsTest {
  public CapsTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("CapsTest", new String[] {});
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  @Test
  public void capsMerge() {
    Caps c1 = new Caps("video/x-raw, format=RGB, bpp=32, depth=24");
    Caps c2 = new Caps("video/x-raw, format=RGB, width=640, height=480");
    Caps c3 = Caps.merge(c1, c2);
    try {
      c1.toString();
      fail("merged caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("merged caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    boolean widthFound = false, heightFound = false;
    for (int i = 0; i < c3.size(); ++i) {
      Structure s = c3.getStructure(i);
      if (s.hasIntField("width")) {
        widthFound = true;
      }
      if (s.hasIntField("height")) {
        heightFound = true;
      }
    }
    assertTrue("width not appended", widthFound);
    assertTrue("height not appended", heightFound);
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void capsAppend() {
    Caps c1 = new Caps("video/x-raw, format=RGB, bpp=32, depth=24");
    Caps c2 = new Caps("video/x-raw, format=RGB, width=640, height=480");
    c1.append(c2);
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    boolean widthFound = false, heightFound = false;
    for (int i = 0; i < c1.size(); ++i) {
      Structure s = c1.getStructure(i);
      if (s.hasIntField("width")) {
        widthFound = true;
      }
      if (s.hasIntField("height")) {
        heightFound = true;
      }
    }
    assertTrue("width not appended", widthFound);
    assertTrue("height not appended", heightFound);
    Assert.assertEquals(1, c1.getRefCount());
    c1.dispose();
  }

  private static final String non_simple_caps_string = "video/x-raw, format=I420, framerate=(fraction)[ 1/100, 100 ], "
      + "width=(int)[ 16, 4096 ], height=(int)[ 16, 4096 ]; video/x-raw, "
      + "format=YUY2, framerate=(fraction)[ 1/100, 100 ], width=(int)[ 16, 4096 ], "
      + "height=(int)[ 16, 4096 ]; video/x-raw, format=RGB, bpp=(int)8, depth=(int)8, "
      + "endianness=(int)1234, framerate=(fraction)[ 1/100, 100 ], width=(int)[ 16, 4096 ], "
      + "height=(int)[ 16, 4096 ]; video/x-raw, "
      + "format={ I420, YUY2, YV12 }, width=(int)[ 16, 4096 ], "
      + "height=(int)[ 16, 4096 ], framerate=(fraction)[ 1/100, 100 ]";

  @Test
  public void simplify() {
    Caps c1 = new Caps(non_simple_caps_string);
    assertNotNull("Caps not created", c1);
    Caps c2 = c1.simplify();
    assertNotNull("Simplify returned null", c2);
    assertEquals("Caps not simplified to 2 structures", 2, c2.size());
    Structure s1 = c2.getStructure(0);
    assertNotNull("Caps.getStructure(0) failed", s1);
    Structure s2 = c2.getStructure(1);
    assertNotNull("Caps.getStructure(1) failed", s2);
    if (!s1.hasName("video/x-raw")) {
      Structure tmp = s1;
      s1 = s2;
      s2 = tmp;
    }
    assertTrue("Could not locate video/x-raw structure", s1.hasName("video/x-raw"));
    assertEquals("bpp not retrieved", 8, s1.getInteger("bpp"));
    assertEquals("depth not retrieved", 8, s1.getInteger("depth"));
    assertTrue("Could not locate video/x-raw structure", s2.hasName("video/x-raw"));
    Assert.assertEquals(1, c1.getRefCount());
    Assert.assertEquals(1, c2.getRefCount());
    c1.dispose();
    c2.dispose();
  }

  @Test
  public void truncate() {
    Caps c1 = Caps.fromString(non_simple_caps_string);
    assertNotNull("Caps.fromString failed", c1);
    assertEquals("Incorrect number of structures in caps", 4, c1.size());
    Caps c2 = c1.truncate();
    assertEquals("Caps not truncated", 1, c2.size());
    assertEquals("Original caps untouched", 4, c1.size());
    Assert.assertEquals(1, c1.getRefCount());
    Assert.assertEquals(1, c2.getRefCount());
    c1.dispose();
    c2.dispose();
  }

  @Test
  public void mergeANYAndSpecific() {
    Caps c1 = Caps.anyCaps();
    Caps c2 = Caps.fromString("audio/x-raw,rate=44100");
    Caps c3 = Caps.merge(c1, c2);
    assertEquals("Too many structures in merged caps", 0, c3.size());
    assertTrue("Merged caps should be ANY", c3.isAny());
    try {
      c1.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void mergeSpecificAndANY() {
    Caps c1 = Caps.fromString("audio/x-raw,rate=44100");
    Caps c2 = Caps.anyCaps();
    Caps c3 = Caps.merge(c1, c2);
    assertEquals("Too many structures in merged caps", 0, c3.size());
    assertTrue("Merged caps should be ANY", c3.isAny());
    try {
      c1.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void mergeSpecificAndEMPTY() {
    Caps c1 = Caps.fromString("audio/x-raw,rate=44100");
    Caps c2 = Caps.emptyCaps();
    Caps c3 = Caps.merge(c1, c2);
    assertEquals("Wrong number of structures in merged structure", 1, c3.size());
    assertFalse("Merged caps should not be empty", c3.isEmpty());
    try {
      c1.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void mergeEMPTYAndSpecific() {
    Caps c1 = Caps.emptyCaps();
    Caps c2 = Caps.fromString("audio/x-raw,rate=44100");
    Caps c3 = Caps.merge(c1, c2);
    assertEquals("Merged Caps structure count incorrect", 1, c3.size());
    assertFalse("Merged caps should not be empty", c3.isEmpty());
    try {
      c1.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void mergeSame() {
    Caps c1 = Caps.fromString("audio/x-raw,rate=44100,channels=1");
    Caps c2 = Caps.fromString("audio/x-raw,rate=44100,channels=1");
    Caps c3 = Caps.merge(c1, c2);
    assertEquals("Merged Caps structure count incorrect", 1, c3.size());
    try {
      c1.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void mergeSameWithDifferentOrder() {
    Caps c1 = Caps.fromString("audio/x-raw,rate=44100,channels=1");
    Caps c2 = Caps.fromString("audio/x-raw,channels=1,rate=44100");
    Caps c3 = Caps.merge(c1, c2);
    assertEquals("Merged Caps structure count incorrect", 1, c3.size());
    try {
      c1.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void mergeSameWithBufferData() {
    Caps c1 = Caps.fromString("video/x-foo, data=(buffer)AA");
    Caps c2 = Caps.fromString("video/x-foo, data=(buffer)AABB");
    Caps c3 = Caps.merge(c1, c2);
    assertEquals("Merged Caps structure count incorrect", 2, c3.size());
    try {
      c1.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void mergeSameWithBufferDataReversed() {
    Caps c1 = Caps.fromString("video/x-foo, data=(buffer)AABB");
    Caps c2 = Caps.fromString("video/x-foo, data=(buffer)AA");
    Caps c3 = Caps.merge(c1, c2);
    assertEquals("Merged Caps structure count incorrect", 2, c3.size());
    try {
      c1.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void mergeSameWithBufferDataSame() {
    Caps c1 = Caps.fromString("video/x-foo, data=(buffer)AA");
    Caps c2 = Caps.fromString("video/x-foo, data=(buffer)AA");
    Caps c3 = Caps.merge(c1, c2);
    assertEquals("Merged Caps structure count incorrect", 1, c3.size());
    try {
      c1.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void mergeDifferentWithBufferDataSame() {
    Caps c1 = Caps.fromString("video/x-foo, data=(buffer)AA");
    Caps c2 = Caps.fromString("video/x-bar, data=(buffer)AA");
    Caps c3 = Caps.merge(c1, c2);
    assertEquals("Merged Caps structure count incorrect", 2, c3.size());
    try {
      c1.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void mergeSubset() {
    Caps c2 = Caps.fromString("audio/x-raw,channels=[1,2]");
    Caps c1 = Caps.fromString("audio/x-raw,channels=1");
    Caps c3 = Caps.merge(c1, c2).simplify();
    System.out.println(c3.toString());
    assertEquals("Merged Caps structure count incorrect", 1, c3.size());
    try {
      c1.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    try {
      c2.toString();
      fail("appended caps not invalidated");
    } catch (IllegalStateException ex) {
    }
    Assert.assertEquals(1, c3.getRefCount());
    c3.dispose();
  }

  @Test
  public void intersect() {
    Caps c2 = Caps.fromString("video/x-raw,format=I420,width=20");
    Caps c1 = Caps.fromString("video/x-raw,format=I420,height=30");
    Caps ci1 = c2.intersect(c1);
    assertEquals("Intersected Caps structure count incorrect", 1, ci1.size());
    Structure s = ci1.getStructure(0);
    assertTrue("Incorrect name on intersected structure", s.hasName("video/x-raw"));
    assertTrue("Intersected structure does not have 'format' field", s.hasField("format"));
    assertTrue("Intersected structure does not have 'width' field", s.hasField("width"));
    assertTrue("Intersected structure does not have 'height' field", s.hasField("height"));
    Caps ci2 = c1.intersect(c2);
    assertEquals("Intersected Caps structure count incorrect", 1, ci2.size());
    s = ci2.getStructure(0);
    assertTrue("Incorrect name on intersected structure", s.hasName("video/x-raw"));
    assertTrue("Intersected structure does not have 'format' field", s.hasField("format"));
    assertTrue("Intersected structure does not have 'width' field", s.hasField("width"));
    assertTrue("Intersected structure does not have 'height' field", s.hasField("height"));
    assertTrue("Intersection should be same in both directions", ci1.isEqual(ci2));
    c2.dispose();
    c1.dispose();
    ci1.dispose();
    ci2.dispose();
  }

  @Test
  public void intersectUnspecified() {
    Caps c2 = Caps.fromString("video/x-raw,format=I420,width=20");
    Caps c1 = Caps.fromString("video/x-raw,format=I420");
    Caps ci1 = c2.intersect(c1);
    assertEquals("Intersected Caps structure count incorrect", 1, ci1.size());
    Structure s = ci1.getStructure(0);
    assertTrue("Incorrect name on intersected structure", s.hasName("video/x-raw"));
    assertTrue("Intersected structure does not have 'format' field", s.hasField("format"));
    assertTrue("Intersected structure does not have 'width' field", s.hasField("width"));
    Caps ci2 = c1.intersect(c2);
    assertEquals("Intersected Caps structure count incorrect", 1, ci2.size());
    s = ci2.getStructure(0);
    assertTrue("Incorrect name on intersected structure", s.hasName("video/x-raw"));
    assertTrue("Intersected structure does not have 'format' field", s.hasField("format"));
    assertTrue("Intersected structure does not have 'width' field", s.hasField("width"));
    assertTrue("Intersection should be same in both directions", ci1.isEqual(ci2));
    c2.dispose();
    c1.dispose();
    ci1.dispose();
    ci2.dispose();
  }

  @Test
  public void intersectUnequal() {
    Caps c2 = Caps.fromString("video/x-raw,format=I420,width=20");
    Caps c1 = Caps.fromString("video/x-raw,format=I420,width=30");
    Caps ci1 = c2.intersect(c1);
    assertTrue("Intersection of unequal caps should be empty", ci1.isEmpty());
    Caps ci2 = c1.intersect(c2);
    assertTrue("Intersection of unequal caps should be empty", ci1.isEmpty());
    assertTrue("Intersection should be same in both directions", ci1.isEqual(ci2));
    c2.dispose();
    c1.dispose();
    ci1.dispose();
    ci2.dispose();
  }

  @Test
  public void intersectDifferentType() {
    Caps c2 = Caps.fromString("video/x-raw,format=I420,width=20");
    Caps c1 = Caps.fromString("video/x-raw,format=RGB,width=20");
    Caps ci1 = c2.intersect(c1);
    assertTrue("Intersection of different type caps should be empty", ci1.isEmpty());
    Caps ci2 = c1.intersect(c2);
    assertTrue("Intersection of different type caps should be empty", ci1.isEmpty());
    assertTrue("Intersection should be same in both directions", ci1.isEqual(ci2));
    c2.dispose();
    c1.dispose();
    ci1.dispose();
    ci2.dispose();
  }
}
