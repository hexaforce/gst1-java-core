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

package org.freedesktop.gstreamer.elements;

import java.io.File;
import java.net.URI;
import java.util.EnumSet;
import java.util.Set;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Pad;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.TagList;
import org.freedesktop.gstreamer.glib.NativeFlags;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;

@SuppressWarnings("unused")
public class PlayBin extends Pipeline {
  public static final String GST_NAME = "playbin";
  public static final String GTYPE_NAME = "GstPlayBin";

  public PlayBin(String name) {
    this(makeRawElement(GST_NAME, name));
  }

  public PlayBin(String name, URI uri) {
    this(name);
    setURI(uri);
  }

  PlayBin(Initializer init) {
    super(init);
  }

  public void setInputFile(File file) {
    setURI(file.toURI());
  }

  public void setURI(URI uri) {
    set("uri", uri);
  }

  public void setAudioSink(Element element) {
    setElement("audio-sink", element);
  }

  public void setVideoSink(Element element) {
    setElement("video-sink", element);
  }

  public void setTextSink(Element element) {
    setElement("text-sink", element);
  }

  public void setVisualization(Element element) {
    setElement("vis-plugin", element);
  }

  private void setElement(String key, Element element) {
    set(key, element);
  }

  public void setFlags(Set<PlayFlags> flags) {
    set("flags", NativeFlags.toInt(flags));
  }

  public Set<PlayFlags> getFlags() {
    Object flags = get("flags");
    if (flags instanceof Number) {
      return NativeFlags.fromInt(PlayFlags.class, ((Number) flags).intValue());
    } else {
      return EnumSet.noneOf(PlayFlags.class);
    }
  }

  public void setVolume(double volume) {
    set("volume", Math.max(Math.min(volume, 1d), 0d));
  }

  public double getVolume() {
    return ((Number) get("volume")).doubleValue();
  }

  public int getCurrentAudio() {
    return (Integer) get("current-audio");
  }

  public void setCurrentAudio(int n) {
    set("current-audio", n);
  }

  public int getNAudio() {
    return (Integer) get("n-audio");
  }

  public Pad getAudioPad(int audioStreamIndex) {
    return emit(Pad.class, "get-audio-pad", audioStreamIndex);
  }

  public TagList getAudioTags(int audioStreamIndex) {
    return emit(TagList.class, "get-audio-tags", audioStreamIndex);
  }

  public int getCurrentText() {
    return (Integer) get("current-text");
  }

  public void setCurrentText(int n) {
    set("current-text", n);
  }

  public int getNText() {
    return (Integer) get("n-text");
  }

  public Pad getTextPad(int textStreamIndex) {
    return emit(Pad.class, "get-text-pad", textStreamIndex);
  }

  public TagList getTextTags(int textStreamIndex) {
    return emit(TagList.class, "get-text-tags", textStreamIndex);
  }

  public void connect(final ABOUT_TO_FINISH listener) {
    connect(ABOUT_TO_FINISH.class, listener, new GstCallback() {
      public void callback(PlayBin elem) {
        listener.aboutToFinish(elem);
      }
    });
  }

  public void disconnect(ABOUT_TO_FINISH listener) {
    disconnect(ABOUT_TO_FINISH.class, listener);
  }

  public void connect(final VIDEO_CHANGED listener) {
    connect(VIDEO_CHANGED.class, listener, new GstCallback() {
      public void callback(PlayBin elem) {
        listener.videoChanged(elem);
      }
    });
  }

  public void disconnect(VIDEO_CHANGED listener) {
    disconnect(VIDEO_CHANGED.class, listener);
  }

  public void connect(final AUDIO_CHANGED listener) {
    connect(AUDIO_CHANGED.class, listener, new GstCallback() {
      public void callback(PlayBin elem) {
        listener.audioChanged(elem);
      }
    });
  }

  public void disconnect(AUDIO_CHANGED listener) {
    disconnect(AUDIO_CHANGED.class, listener);
  }

  public void connect(final TEXT_CHANGED listener) {
    connect(TEXT_CHANGED.class, listener, new GstCallback() {
      public void callback(PlayBin elem) {
        listener.textChanged(elem);
      }
    });
  }

  public void disconnect(TEXT_CHANGED listener) {
    disconnect(TEXT_CHANGED.class, listener);
  }

  public void connect(final VIDEO_TAGS_CHANGED listener) {
    connect(VIDEO_TAGS_CHANGED.class, listener, new GstCallback() {
      public void callback(PlayBin elem, int stream) {
        listener.videoTagsChanged(elem, stream);
      }
    });
  }

  public void disconnect(VIDEO_TAGS_CHANGED listener) {
    disconnect(VIDEO_TAGS_CHANGED.class, listener);
  }

  public void connect(final AUDIO_TAGS_CHANGED listener) {
    connect(AUDIO_TAGS_CHANGED.class, listener, new GstCallback() {
      public void callback(PlayBin elem, int stream) {
        listener.audioTagsChanged(elem, stream);
      }
    });
  }

  public void disconnect(AUDIO_TAGS_CHANGED listener) {
    disconnect(AUDIO_TAGS_CHANGED.class, listener);
  }

  public void connect(final TEXT_TAGS_CHANGED listener) {
    connect(TEXT_TAGS_CHANGED.class, listener, new GstCallback() {
      public void callback(PlayBin elem, int stream) {
        listener.textTagsChanged(elem, stream);
      }
    });
  }

  public void disconnect(TEXT_TAGS_CHANGED listener) {
    disconnect(TEXT_TAGS_CHANGED.class, listener);
  }

  @Gst.Since(minor = 10)
  public void connect(final ELEMENT_SETUP listener) {
    Gst.checkVersion(1, 10);
    connect(ELEMENT_SETUP.class, listener, new GstCallback() {
      public void callback(PlayBin playbin, Element element) {
        listener.elementSetup(playbin, element);
      }
    });
  }

  @Gst.Since(minor = 10)
  public void disconnect(ELEMENT_SETUP listener) {
    disconnect(ELEMENT_SETUP.class, listener);
  }

  public void connect(final SOURCE_SETUP listener) {
    connect(SOURCE_SETUP.class, listener, new GstCallback() {
      public void callback(PlayBin playbin, Element element) {
        listener.sourceSetup(playbin, element);
      }
    });
  }

  public void disconnect(SOURCE_SETUP listener) {
    disconnect(SOURCE_SETUP.class, listener);
  }

  public static interface ABOUT_TO_FINISH {
    public void aboutToFinish(PlayBin element);
  }

  public static interface VIDEO_CHANGED {
    public void videoChanged(PlayBin element);
  }

  public static interface AUDIO_CHANGED {
    public void audioChanged(PlayBin element);
  }

  public static interface TEXT_CHANGED {
    public void textChanged(PlayBin element);
  }

  public static interface VIDEO_TAGS_CHANGED {
    public void videoTagsChanged(PlayBin element, int stream);
  }

  public static interface AUDIO_TAGS_CHANGED {
    public void audioTagsChanged(PlayBin element, int stream);
  }

  public static interface TEXT_TAGS_CHANGED {
    public void textTagsChanged(PlayBin element, int stream);
  }

  @Gst.Since(minor = 10)
  public static interface ELEMENT_SETUP {
    public void elementSetup(PlayBin playbin, Element element);
  }

  public static interface SOURCE_SETUP {
    public void sourceSetup(PlayBin playbin, Element element);
  }
}
