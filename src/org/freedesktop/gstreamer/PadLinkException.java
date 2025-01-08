package org.freedesktop.gstreamer;

@SuppressWarnings("serial")
public class PadLinkException extends GstException {
  private final PadLinkReturn linkResult;

  PadLinkException(PadLinkReturn result) {
    this("failed to link pads (" + result + ")", result);
  }

  PadLinkException(String message, PadLinkReturn result) {
    super(message);
    linkResult = result;
  }

  public PadLinkReturn getLinkResult() {
    return linkResult;
  }
}
