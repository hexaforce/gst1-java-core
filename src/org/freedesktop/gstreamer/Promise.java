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

import static org.freedesktop.gstreamer.lowlevel.GstPromiseAPI.GSTPROMISE_API;

import com.sun.jna.Pointer;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;

@SuppressWarnings("unused")
@Gst.Since(minor = 14)
public class Promise extends MiniObject {
  public static final String GTYPE_NAME = "GstPromise";
  private GstCallback changeFunction;

  Promise(final Initializer init) {
    super(init);
  }

  public Promise() {
    this(Natives.initializer(GSTPROMISE_API.ptr_gst_promise_new()));
    Gst.checkVersion(1, 14);
  }

  public Promise(final PROMISE_CHANGE listener) {
    this(new GstCallback() {
      public void callback(Promise promise, Pointer userData) {
        listener.onChange(promise);
      }
    });
  }

  private Promise(GstCallback callback) {
    this(Natives.initializer(GSTPROMISE_API.ptr_gst_promise_new_with_change_func(callback, null, null)));
    this.changeFunction = callback;
  }

  public PromiseResult waitResult() {
    return GSTPROMISE_API.gst_promise_wait(this);
  }

  public void reply(final Structure structure) {
    GSTPROMISE_API.gst_promise_reply(this, structure);
  }

  public void interrupt() {
    GSTPROMISE_API.gst_promise_interrupt(this);
  }

  public void expire() {
    GSTPROMISE_API.gst_promise_expire(this);
  }

  public Structure getReply() {
    return Structure.objectFor(GSTPROMISE_API.ptr_gst_promise_get_reply(this), false, false);
  }

  public static interface PROMISE_CHANGE {
    public void onChange(Promise promise);
  }
}
