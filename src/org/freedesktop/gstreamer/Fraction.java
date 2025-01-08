package org.freedesktop.gstreamer;

public class Fraction {
  public final int numerator;
  public final int denominator;

  public Fraction(int numerator, int denominator) {
    this.numerator = numerator;
    this.denominator = denominator;
  }

  public int getNumerator() {
    return numerator;
  }

  public int getDenominator() {
    return denominator;
  }

  public double toDouble() {
    return denominator != 0 ? ((double) numerator / denominator) : Double.NaN;
  }
}
