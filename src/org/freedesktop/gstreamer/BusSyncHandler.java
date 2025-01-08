package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.message.Message;

public interface BusSyncHandler {
  public BusSyncReply syncMessage(Message message);
}
