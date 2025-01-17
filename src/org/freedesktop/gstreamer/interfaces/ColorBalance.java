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

package org.freedesktop.gstreamer.interfaces;

import static org.freedesktop.gstreamer.lowlevel.GstColorBalanceAPI.GSTCOLORBALANCE_API;

import com.sun.jna.Pointer;
import java.util.ArrayList;
import java.util.List;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.lowlevel.GlibAPI;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;

@SuppressWarnings("unused")
public class ColorBalance extends GstInterface {
  public static final ColorBalance wrap(Element element) {
    return new ColorBalance(element);
  }

  private ColorBalance(Element element) {
    super(element, GSTCOLORBALANCE_API.gst_color_balance_get_type());
  }

  public List<ColorBalanceChannel> getChannelList() {
    GlibAPI.GList glist = GSTCOLORBALANCE_API.gst_color_balance_list_channels(this);
    List<ColorBalanceChannel> list = new ArrayList<>();
    GlibAPI.GList next = glist;
    while (next != null) {
      if (next.data != null) {
        list.add(channelFor(next.data, true));
      }
      next = next.next();
    }
    return list;
  }

  private final ColorBalanceChannel channelFor(Pointer pointer, boolean needRef) {
    return new ColorBalanceChannel(this, pointer, needRef, true);
  }

  public static interface VALUE_CHANGED {
    public void colorBalanceValueChanged(ColorBalance colorBalance, ColorBalanceChannel channel, int value);
  }

  public void connect(final VALUE_CHANGED listener) {
    element.connect(VALUE_CHANGED.class, listener, new GstCallback() {
      public boolean callback(Pointer colorBalance, ColorBalanceChannel channel, int value) {
        listener.colorBalanceValueChanged(ColorBalance.this, channel, value);
        return true;
      }
    });
  }

  public void disconnect(VALUE_CHANGED listener) {
    element.disconnect(VALUE_CHANGED.class, listener);
  }
}
