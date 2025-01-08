package org.freedesktop.gstreamer;

public final class Segment {
  private final double rate;
  private final Format format;
  private final long startValue;
  private final long stopValue;

  Segment(double rate, Format format, long startValue, long stopValue) {
    this.rate = rate;
    this.format = format;
    this.stopValue = stopValue;
    this.startValue = startValue;
  }

  public double getRate() {
    return rate;
  }

  public Format getFormat() {
    return format;
  }

  public long getStartValue() {
    return startValue;
  }

  public long getStopValue() {
    return stopValue;
  }
}
