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

import static org.freedesktop.gstreamer.lowlevel.GstPadTemplateAPI.GSTPADTEMPLATE_API;

import org.freedesktop.gstreamer.glib.Natives;

public class PadTemplate extends GstObject {
  public static final String GTYPE_NAME = "GstPadTemplate";

  PadTemplate(Initializer init) {
    super(init);
  }

  public PadTemplate(String nameTemplate, PadDirection direction, Caps caps) {
    this(Natives.initializer(GSTPADTEMPLATE_API.ptr_gst_pad_template_new(nameTemplate, direction, PadPresence.ALWAYS, caps)));
  }

  public PadTemplate(String nameTemplate, PadDirection direction, PadPresence presence, Caps caps) {
    this(Natives.initializer(GSTPADTEMPLATE_API.ptr_gst_pad_template_new(nameTemplate, direction, presence, caps)));
  }

  public Caps getCaps() {
    return GSTPADTEMPLATE_API.gst_pad_template_get_caps(this);
  }

  public String getTemplateName() {
    return get("name-template").toString();
  }

  public PadDirection getDirection() {
    Object d = get("direction");
    if (d instanceof Number) {
      return PadDirection.values()[((Number) d).intValue()];
    }
    return null;
  }

  public PadPresence getPresence() {
    Object p = get("presence");
    if (p instanceof Number) {
      return PadPresence.values()[((Number) p).intValue()];
    }
    return null;
  }
}
