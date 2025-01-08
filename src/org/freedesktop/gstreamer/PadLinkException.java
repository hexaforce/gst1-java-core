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
