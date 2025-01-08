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
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GPointer;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.freedesktop.gstreamer.util.TestAssumptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PromiseTest {
  public PromiseTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init(Gst.getVersion(), "PromiseTest");
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  @Test
  public void testReply() {
    TestAssumptions.requireGstVersion(1, 14);
    Promise promise = new Promise();
    promise.reply(null);
    PromiseResult promiseStatus = promise.waitResult();
    assertEquals("promise reply state not correct", promiseStatus, PromiseResult.REPLIED);
  }

  @Test
  public void testInterrupt() {
    TestAssumptions.requireGstVersion(1, 14);
    Promise promise = new Promise();
    promise.interrupt();
    PromiseResult promiseStatus = promise.waitResult();
    assertEquals("promise reply state not correct", promiseStatus, PromiseResult.INTERRUPTED);
  }

  @Test
  public void testExpire() {
    TestAssumptions.requireGstVersion(1, 14);
    Promise promise = new Promise();
    promise.expire();
    PromiseResult promiseStatus = promise.waitResult();
    assertEquals("promise reply state not correct", promiseStatus, PromiseResult.EXPIRED);
  }

  @Test
  public void testInvalidateReply() {
    TestAssumptions.requireGstVersion(1, 14);
    Promise promise = new Promise();
    Structure data = new Structure("data");
    assertTrue(Natives.ownsReference(data));
    promise.reply(data);
    assertFalse(Natives.ownsReference(data));
    assertFalse(Natives.validReference(data));
  }

  @Test
  public void testReplyData() {
    TestAssumptions.requireGstVersion(1, 14);
    Promise promise = new Promise();
    Structure data = new Structure("data", "test", GType.UINT, 1);
    GPointer pointer = Natives.getPointer(data);
    promise.reply(data);
    assertEquals("promise state not in replied", promise.waitResult(), PromiseResult.REPLIED);
    Structure result = promise.getReply();
    assertEquals("result of promise does not match reply", pointer, Natives.getPointer(result));
  }

  @Test
  public void testDispose() {
    TestAssumptions.requireGstVersion(1, 14);
    Promise promise = new Promise();
    promise.interrupt();
    promise.dispose();
  }

  @Test
  public void testDisposeWithChangeFunc() {
    TestAssumptions.requireGstVersion(1, 14);
    Promise promise = new Promise(new Promise.PROMISE_CHANGE() {
      @Override
      public void onChange(Promise promise) {}
    });
    promise.interrupt();
    promise.dispose();
  }

  @Test
  public void testChangeFunctionGC() {
    TestAssumptions.requireGstVersion(1, 14);
    final AtomicBoolean onChangeFired = new AtomicBoolean(false);
    Promise promise = new Promise(new Promise.PROMISE_CHANGE() {
      @Override
      public void onChange(Promise promise) {
        onChangeFired.set(true);
      }
    });
    System.gc();
    System.gc();
    promise.interrupt();
    assertTrue("Promise Change callback GC'd", onChangeFired.get());
    promise.dispose();
  }
}
