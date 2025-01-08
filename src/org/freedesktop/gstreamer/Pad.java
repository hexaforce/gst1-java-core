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

import static org.freedesktop.gstreamer.lowlevel.GstPadAPI.GSTPAD_API;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import java.util.HashSet;
import java.util.Set;
import org.freedesktop.gstreamer.event.Event;
import org.freedesktop.gstreamer.glib.NativeFlags;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;
import org.freedesktop.gstreamer.lowlevel.GstPadAPI;
import org.freedesktop.gstreamer.lowlevel.GstPadProbeInfo;
import org.freedesktop.gstreamer.lowlevel.GstPadPtr;

@SuppressWarnings("unused")
public class Pad extends GstObject {
  public static final String GTYPE_NAME = "GstPad";
  private static final int EVENT_HAS_INFO_MASK = GstPadAPI.GST_PAD_PROBE_TYPE_EVENT_DOWNSTREAM | GstPadAPI.GST_PAD_PROBE_TYPE_EVENT_UPSTREAM;
  private final Handle handle;

  Pad(Initializer init) {
    this(new Handle(init.ptr.as(GstPadPtr.class, GstPadPtr::new), init.ownsHandle), init.needRef);
  }

  private Pad(Handle handle, boolean needRef) {
    super(handle, needRef);
    this.handle = handle;
  }

  public Pad(String name, PadDirection direction) {
    this(Natives.initializer(GSTPAD_API.ptr_gst_pad_new(name, direction), false));
  }

  public Pad(PadTemplate template, String name) {
    this(Natives.initializer(GSTPAD_API.ptr_gst_pad_new_from_template(template, name), false));
  }

  public Caps queryCaps(Caps filter) {
    return GSTPAD_API.gst_pad_query_caps(this, filter);
  }

  public Caps getAllowedCaps() {
    return GSTPAD_API.gst_pad_get_allowed_caps(this);
  }

  public Caps getCurrentCaps() {
    return GSTPAD_API.gst_pad_get_current_caps(this);
  }

  public Pad getPeer() {
    return GSTPAD_API.gst_pad_get_peer(this);
  }

  public Caps peerQueryCaps(Caps filter) {
    return GSTPAD_API.gst_pad_peer_query_caps(this, filter);
  }

  public boolean queryAcceptCaps(Caps caps) {
    return GSTPAD_API.gst_pad_query_accept_caps(this, caps);
  }

  public boolean peerQueryAcceptCaps(Caps caps) {
    return GSTPAD_API.gst_pad_peer_query_accept_caps(this, caps);
  }

  public void link(Pad sink) throws PadLinkException {
    PadLinkReturn result = GSTPAD_API.gst_pad_link(this, sink);
    if (result != PadLinkReturn.OK) {
      throw new PadLinkException(result);
    }
  }

  public boolean unlink(Pad pad) {
    return GSTPAD_API.gst_pad_unlink(this, pad);
  }

  public boolean isLinked() {
    return GSTPAD_API.gst_pad_is_linked(this);
  }

  public PadDirection getDirection() {
    return GSTPAD_API.gst_pad_get_direction(this);
  }

  public Element getParentElement() {
    return GSTPAD_API.gst_pad_get_parent_element(this);
  }

  public boolean setActive(boolean active) {
    return GSTPAD_API.gst_pad_set_active(this, active);
  }

  public boolean isBlocked() {
    return GSTPAD_API.gst_pad_is_blocked(this);
  }

  public void block(final Runnable callback) {
    addEventProbe(new EVENT_PROBE() {
      public PadProbeReturn eventReceived(Pad pad, Event event) {
        callback.run();
        pad.removeCallback(EVENT_PROBE.class, this);
        return PadProbeReturn.REMOVE;
      }
    }, GstPadAPI.GST_PAD_PROBE_TYPE_IDLE);
  }

  public boolean isBlocking() {
    return GSTPAD_API.gst_pad_is_blocking(this);
  }

  public void connect(final LINKED listener) {
    connect(LINKED.class, listener, new GstCallback() {
      public boolean callback(Pad pad, Pad peer) {
        listener.linked(pad, peer);
        return true;
      }
    });
  }

  public void disconnect(LINKED listener) {
    disconnect(LINKED.class, listener);
  }

  public void connect(final UNLINKED listener) {
    connect(UNLINKED.class, listener, new GstCallback() {
      public boolean callback(Pad pad, Pad peer) {
        listener.unlinked(pad, peer);
        return true;
      }
    });
  }

  public void disconnect(UNLINKED listener) {
    disconnect(UNLINKED.class, listener);
  }

  public void addProbe(final Set<PadProbeType> mask, PROBE callback) {
    addProbe(NativeFlags.toInt(mask), callback);
  }

  public void addProbe(PadProbeType mask, PROBE callback) {
    addProbe(mask.intValue(), callback);
  }

  synchronized void addProbe(int mask, PROBE callback) {
    final GstPadAPI.PadProbeCallback probe = new GstPadAPI.PadProbeCallback() {
      @Override
      public PadProbeReturn callback(Pad pad, GstPadProbeInfo probeInfo, Pointer user_data) {
        PadProbeInfo info = new PadProbeInfo(probeInfo);
        PadProbeReturn ret = callback.probeCallback(pad, info);
        info.invalidate();
        if (ret == PadProbeReturn.REMOVE) {
          handle.probes.remove(probeInfo.id);
          removeCallback(PROBE.class, callback);
        }
        return ret;
      }
    };
    NativeLong id = handle.addProbe(mask, probe);
    if (id.longValue() == 0) {
      return;
    }
    GCallback cb = new GCallback(id, probe) {
      @Override
      protected void disconnect() {
        handle.removeProbe(id);
      }
    };
    addCallback(PROBE.class, callback, cb);
  }

  public synchronized void removeProbe(PROBE callback) {
    removeCallback(PROBE.class, callback);
  }

  public void addEventProbe(final EVENT_PROBE listener) {
    final int mask = GstPadAPI.GST_PAD_PROBE_TYPE_EVENT_BOTH | GstPadAPI.GST_PAD_PROBE_TYPE_EVENT_FLUSH;
    addEventProbe(listener, mask);
  }

  synchronized void addEventProbe(final EVENT_PROBE listener, final int mask) {
    final GstPadAPI.PadProbeCallback probe = new GstPadAPI.PadProbeCallback() {
      public PadProbeReturn callback(Pad pad, GstPadProbeInfo probeInfo, Pointer user_data) {
        if ((probeInfo.padProbeType & mask) != 0) {
          Event event = null;
          if ((probeInfo.padProbeType & EVENT_HAS_INFO_MASK) != 0) {
            event = GSTPAD_API.gst_pad_probe_info_get_event(probeInfo);
          }
          PadProbeReturn ret = listener.eventReceived(pad, event);
          if (ret == PadProbeReturn.REMOVE) {
            handle.probes.remove(probeInfo.id);
            removeCallback(EVENT_PROBE.class, listener);
          }
          return ret;
        }
        return PadProbeReturn.OK;
      }
    };
    NativeLong id = handle.addProbe(mask, probe);
    if (id.longValue() == 0) {
      return;
    }
    GCallback cb = new GCallback(id, probe) {
      @Override
      protected void disconnect() {
        handle.removeProbe(id);
      }
    };
    addCallback(EVENT_PROBE.class, listener, cb);
  }

  public void removeEventProbe(EVENT_PROBE listener) {
    removeCallback(EVENT_PROBE.class, listener);
  }

  public synchronized void addDataProbe(final DATA_PROBE listener) {
    final GstPadAPI.PadProbeCallback probe = new GstPadAPI.PadProbeCallback() {
      public PadProbeReturn callback(Pad pad, GstPadProbeInfo probeInfo, Pointer user_data) {
        if ((probeInfo.padProbeType & GstPadAPI.GST_PAD_PROBE_TYPE_BUFFER) != 0) {
          Buffer buffer = GSTPAD_API.gst_pad_probe_info_get_buffer(probeInfo);
          PadProbeReturn ret = listener.dataReceived(pad, buffer);
          if (ret == PadProbeReturn.REMOVE) {
            handle.probes.remove(probeInfo.id);
            removeCallback(DATA_PROBE.class, listener);
          }
          return ret;
        }
        return PadProbeReturn.OK;
      }
    };
    GCallback cb = new GCallback(handle.addProbe(GstPadAPI.GST_PAD_PROBE_TYPE_BUFFER, probe), probe) {
      @Override
      protected void disconnect() {
        handle.removeProbe(id);
      }
    };
    addCallback(DATA_PROBE.class, listener, cb);
  }

  public void removeDataProbe(DATA_PROBE listener) {
    removeCallback(DATA_PROBE.class, listener);
  }

  public boolean sendEvent(Event event) {
    return GSTPAD_API.gst_pad_send_event(this, event);
  }

  public boolean pushEvent(Event event) {
    return GSTPAD_API.gst_pad_push_event(this, event);
  }

  public FlowReturn chain(Buffer buffer) {
    return GSTPAD_API.gst_pad_chain(this, buffer);
  }

  public FlowReturn getRange(long offset, int size, Buffer[] buffer) {
    return GSTPAD_API.gst_pad_get_range(this, offset, size, buffer);
  }

  public FlowReturn pullRange(long offset, int size, Buffer[] buffer) {
    return GSTPAD_API.gst_pad_pull_range(this, offset, size, buffer);
  }

  public FlowReturn push(final Buffer buffer) {
    return GSTPAD_API.gst_pad_push(this, buffer);
  }

  public PadTemplate getTemplate() {
    return GSTPAD_API.gst_pad_get_pad_template(this);
  }

  public boolean hasCurrentCaps() {
    return GSTPAD_API.gst_pad_has_current_caps(this);
  }

  public static interface LINKED {
    public void linked(Pad pad, Pad peer);
  }

  public static interface UNLINKED {
    public void unlinked(Pad pad, Pad peer);
  }

  public static interface PROBE {
    public PadProbeReturn probeCallback(Pad pad, PadProbeInfo info);
  }

  public static interface EVENT_PROBE {
    public PadProbeReturn eventReceived(Pad pad, Event event);
  }

  public static interface DATA_PROBE {
    public PadProbeReturn dataReceived(Pad pad, Buffer buffer);
  }

  private static class Handle extends GstObject.Handle {
    private final Set<NativeLong> probes;

    private Handle(GstPadPtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
      probes = new HashSet<>();
    }

    @Override
    protected GstPadPtr getPointer() {
      return (GstPadPtr) super.getPointer();
    }

    private synchronized NativeLong addProbe(int mask, GstPadAPI.PadProbeCallback probe) {
      NativeLong id = GSTPAD_API.gst_pad_add_probe(getPointer(), mask, probe, null, null);
      if (id.longValue() != 0) {
        probes.add(id);
      }
      return id;
    }

    private synchronized void removeProbe(NativeLong id) {
      if (probes.remove(id)) {
        GSTPAD_API.gst_pad_remove_probe(getPointer(), id);
      }
    }

    private synchronized void clearProbes() {
      probes.forEach(id -> GSTPAD_API.gst_pad_remove_probe(getPointer(), id));
      probes.clear();
    }

    @Override
    public void invalidate() {
      clearProbes();
      super.invalidate();
    }

    @Override
    public void dispose() {
      clearProbes();
      super.dispose();
    }
  }
}
