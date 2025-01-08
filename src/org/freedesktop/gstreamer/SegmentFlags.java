package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.event.SeekFlags;
import org.freedesktop.gstreamer.glib.NativeFlags;

enum SegmentFlags implements NativeFlags<SegmentFlags> {
  RESET(SeekFlags.FLUSH),
  TRICKMODE(SeekFlags.TRICKMODE),
  SEGMENT(SeekFlags.SEGMENT),
  TRICKMODE_KEY_UNITS(SeekFlags.TRICKMODE_KEY_UNITS),
  TRICKMODE_NO_AUDIO(SeekFlags.TRICKMODE_NO_AUDIO);
  private final SeekFlags seekFlags;

  private SegmentFlags(SeekFlags seekFlags) {
    this.seekFlags = seekFlags;
  }

  @Override
  public int intValue() {
    return seekFlags.intValue();
  }
}
