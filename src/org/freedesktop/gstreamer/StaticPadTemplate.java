package org.freedesktop.gstreamer;

public class StaticPadTemplate {
  private final String templateName;
  private final PadDirection direction;
  private final PadPresence presence;
  private final Caps caps;

  StaticPadTemplate(String templateName, PadDirection direction, PadPresence presence, Caps caps) {
    this.templateName = templateName;
    this.direction = direction;
    this.presence = presence;
    this.caps = caps;
  }

  public String getName() {
    return templateName;
  }

  public PadDirection getDirection() {
    return direction;
  }

  public PadPresence getPresence() {
    return presence;
  }

  public Caps getCaps() {
    return caps;
  }
}
