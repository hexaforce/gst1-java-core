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

package org.freedesktop.gstreamer.lowlevel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Gst;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReferenceManagerTest {
  public ReferenceManagerTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("ReferenceManagerTest", new String[] {});
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  public boolean waitGC(Reference<? extends Object> ref) throws InterruptedException {
    System.gc();
    for (int i = 0; ref.get() != null && i < 20; ++i) {
      Thread.sleep(10);
      System.gc();
    }
    return ref.get() == null;
  }

  @Test
  public void testReference() throws Exception {
    Object ref = new Object();
    Caps target = new Caps("video/x-raw");
    ReferenceManager.addKeepAliveReference(ref, target);
    WeakReference<Object> targetRef = new WeakReference<Object>(target);
    target = null;
    assertFalse("target collected prematurely", waitGC(targetRef));
    ref = null;
    assertTrue("target not collected when ref is collected", waitGC(targetRef));
  }

  @Test
  public void testMultipleReferences() throws Exception {
    Object ref1 = new Object();
    Object ref2 = new Object();
    Caps target = new Caps("video/x-raw");
    ReferenceManager.addKeepAliveReference(ref1, target);
    ReferenceManager.addKeepAliveReference(ref2, target);
    WeakReference<Object> targetRef = new WeakReference<Object>(target);
    target = null;
    assertFalse("target collected prematurely", waitGC(targetRef));
    ref1 = null;
    assertFalse("target collected after only one ref disposed", waitGC(targetRef));
    ref2 = null;
    assertTrue("target not collected when ref is dispose", waitGC(targetRef));
  }
}
