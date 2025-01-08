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

package org.freedesktop.gstreamer.message;

import static org.freedesktop.gstreamer.lowlevel.GstMessageAPI.GSTMESSAGE_API;

import org.freedesktop.gstreamer.lowlevel.GstAPI.GErrorStruct;

public class WarningMessage extends GErrorMessage {
  WarningMessage(Initializer init) {
    super(init);
  }

  @Override
  GErrorStruct parseMessage() {
    GErrorStruct[] err = {null};
    GSTMESSAGE_API.gst_message_parse_warning(this, err, null);
    return err[0];
  }
}
