package org.freedesktop.gstreamer;

public class Version {
  public static final Version BASELINE = new Version(1, 8);
  private final int major, minor, micro, nano;

  public Version(int major, int minor) {
    this(major, minor, 0, 0);
  }

  public Version(int major, int minor, int micro, int nano) {
    this.major = major;
    this.minor = minor;
    this.micro = micro;
    this.nano = nano;
  }

  @Override
  public String toString() {
    return String.format("%d.%d.%d%s", major, minor, micro, nano == 1 ? " (CVS)" : nano >= 2 ? " (Pre-release)" : "");
  }

  public int getMajor() {
    return major;
  }

  public int getMinor() {
    return minor;
  }

  public int getMicro() {
    return micro;
  }

  public int getNano() {
    return nano;
  }

  public boolean checkSatisfies(Version required) {
    return (major == required.major && minor > required.minor) || (major == required.major && minor == required.minor && micro >= required.micro);
  }

  public static Version of(int major, int minor) {
    if (major == BASELINE.getMajor() && minor >= BASELINE.getMinor()) {
      return new Version(major, minor);
    }
    throw new IllegalArgumentException("Invalid version");
  }
}
