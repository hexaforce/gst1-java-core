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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class BufferProbeTester {
  public static void test(Consumer<Buffer> callback) {
    test(callback, "videotestsrc ! videoconvert ! fakesink name=sink");
  }

  public static void test(Consumer<Buffer> callback, String pipelineDescription) {
    test(callback, pipelineDescription, 0);
  }

  public static void test(Consumer<Buffer> callback, String pipelineDescription, int skipFrames) {
    assertNotNull("Pipeline description can not be null", pipelineDescription);
    assertFalse("Pipeline description can not be empty", pipelineDescription.isEmpty());
    Pipeline pipe = (Pipeline) Gst.parseLaunch(pipelineDescription);
    assertNotNull("Unable to create Pipeline from pipeline description: ", pipe);
    Element sink = pipe.getElementByName("sink");
    Pad pad = sink.getStaticPad("sink");
    BufferProbe probe = new BufferProbe(callback, skipFrames);
    pad.addDataProbe(probe);
    pipe.play();
    try {
      probe.await(5000);
    } catch (Exception ex) {
      fail("Unexpected exception waiting for buffer\n" + ex);
    } finally {
      pipe.stop();
    }
    if (probe.exception != null) {
      throw new AssertionError(probe.exception);
    }
  }

  private static class BufferProbe implements Pad.DATA_PROBE {
    private final int skipFrames;
    private final CountDownLatch latch;
    private final Consumer<Buffer> callback;
    private Throwable exception;
    private int counter = 0;

    BufferProbe(Consumer<Buffer> callback) {
      this(callback, 0);
    }

    BufferProbe(Consumer<Buffer> callback, int skip) {
      this.callback = callback;
      skipFrames = skip;
      latch = new CountDownLatch(1);
    }

    @Override
    public PadProbeReturn dataReceived(Pad pad, Buffer buffer) {
      if (latch.getCount() > 0) {
        if (counter < skipFrames) {
          counter++;
          return PadProbeReturn.OK;
        }
        try {
          try {
            callback.accept(buffer);
          } catch (Throwable exc) {
            exception = exc;
          }
        } finally {
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
