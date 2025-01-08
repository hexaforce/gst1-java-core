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

import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.glib.Natives;

public class StateChangedMessage extends Message {
  StateChangedMessage(Initializer init) {
    super(init);
  }

  public StateChangedMessage(GstObject src, State old, State current, State pending) {
    super(Natives.initializer(GSTMESSAGE_API.ptr_gst_message_new_state_changed(src, old, current, pending)));
  }

  public State getOldState() {
    State[] state = new State[1];
    GSTMESSAGE_API.gst_message_parse_state_changed(this, state, null, null);
    return state[0];
  }

  public State getNewState() {
    State[] state = new State[1];
    GSTMESSAGE_API.gst_message_parse_state_changed(this, null, state, null);
    return state[0];
  }

  public State getPendingState() {
    State[] state = new State[1];
    GSTMESSAGE_API.gst_message_parse_state_changed(this, null, null, state);
    return state[0];
  }
}
