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
