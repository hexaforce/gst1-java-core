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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

public class ProbeTester {
  public static void test(Set<PadProbeType> mask, Predicate<PadProbeInfo> callback) {
    test("videotestsrc ! fakesink name=sink", mask, callback);
  }

  public static void test(String pipeline, Set<PadProbeType> mask, Predicate<PadProbeInfo> callback) {
    assertNotNull("Pipeline description can not be null", pipeline);
    assertFalse("Pipeline description can not be empty", pipeline.isEmpty());
    Pipeline pipe = (Pipeline) Gst.parseLaunch(pipeline);
    assertNotNull("Unable to create Pipeline from pipeline description: ", pipe);
    Element sink = pipe.getElementByName("sink");
    Pad pad = sink.getStaticPad("sink");
    PadProbe probe = new PadProbe(callback);
    pad.addProbe(mask, probe);
    pipe.play();
    try {
      probe.await(5000);
    } catch (TimeoutException ex) {
      fail("Timed out waiting for probe condition\n" + ex);
    } catch (Exception ex) {
      fail("Unexpected exception waiting for probe\n" + ex);
    } finally {
      pipe.stop();
    }
    if (probe.exception != null) {
      throw new AssertionError(probe.exception);
    }
  }

  private static class PadProbe implements Pad.PROBE {
    private final CountDownLatch latch;
    private final Predicate<PadProbeInfo> callback;
    private Throwable exception;

    PadProbe(Predicate<PadProbeInfo> callback) {
      this.callback = callback;
      latch = new CountDownLatch(1);
    }

    @Override
    public PadProbeReturn probeCallback(Pad pad, PadProbeInfo info) {
      if (latch.getCount() > 0) {
        try {
          if (callback.test(info)) {
            latch.countDown();
          }
        } catch (Throwable exc) {
          exception = exc;
          latch.countDown();
        }
      }
      return PadProbeReturn.OK;
    }

    void await(long millis) throws InterruptedException, TimeoutException {
      if (!latch.await(millis, TimeUnit.MILLISECONDS)) {
        throw new TimeoutException();
      }
    }
  }
}
