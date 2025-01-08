package org.freedesktop.gstreamer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class TestPipe {
  public final Pipeline pipe = new Pipeline("pipe");
  public final Element src = ElementFactory.make("fakesrc", "src");
  public final Element sink = ElementFactory.make("fakesink", "sink");
  public final String name;
  private final CountDownLatch latch = new CountDownLatch(1);

  public TestPipe() {
    this(getInvokingMethod());
  }

  private static String getInvokingMethod() {
    try {
      throw new Exception();
    } catch (Exception ex) {
      return ex.getStackTrace()[2].getMethodName();
    }
  }

  public TestPipe(String name) {
    this.name = name;
    pipe.addMany(src, sink);
    Element.linkMany(src, sink);
  }

  public TestPipe run() {
    try {
      latch.await(250, TimeUnit.MILLISECONDS);
    } catch (Exception ex) {
    }
    return this;
  }

  public TestPipe play() {
    pipe.play();
    return this;
  }

  public Bus getBus() {
    return pipe.getBus();
  }

  public void quit() {
    latch.countDown();
  }

  public void dispose() {
    pipe.stop();
  }
}
