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
