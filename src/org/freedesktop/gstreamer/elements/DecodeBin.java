package org.freedesktop.gstreamer.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.freedesktop.gstreamer.Bin;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.Pad;
import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.GType;
import org.freedesktop.gstreamer.lowlevel.GValueAPI;
import org.freedesktop.gstreamer.lowlevel.GValueAPI.GValueArray;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;
import org.freedesktop.gstreamer.query.Query;

@SuppressWarnings("unused")
public class DecodeBin extends Bin {
  public static final String GST_NAME = "decodebin";
  public static final String GTYPE_NAME = "GstDecodeBin";

  public DecodeBin(String name) {
    super(makeRawElement(GST_NAME, name));
  }

  DecodeBin(Initializer init) {
    super(init);
  }

  public static interface UNKNOWN_TYPE {
    public void unknownType(DecodeBin element, Pad pad, Caps caps);
  }

  public void connect(final UNKNOWN_TYPE listener) {
    connect(UNKNOWN_TYPE.class, listener, new GstCallback() {
      public void callback(DecodeBin elem, Pad pad, Caps caps) {
        listener.unknownType(elem, pad, caps);
      }
    });
  }

  public void disconnect(UNKNOWN_TYPE listener) {
    disconnect(UNKNOWN_TYPE.class, listener);
  }

  public static interface AUTOPLUG_CONTINUE {
    public boolean autoplugContinue(DecodeBin element, Pad pad, Caps caps);
  }

  public void connect(final AUTOPLUG_CONTINUE listener) {
    connect(AUTOPLUG_CONTINUE.class, listener, new GstCallback() {
      public boolean callback(DecodeBin elem, Pad pad, Caps caps) {
        return listener.autoplugContinue(elem, pad, caps);
      }
    });
  }

  public void disconnect(AUTOPLUG_CONTINUE listener) {
    disconnect(AUTOPLUG_CONTINUE.class, listener);
  }

  public static interface AUTOPLUG_FACTORIES {
    public Optional<List<ElementFactory>> autoplugFactories(DecodeBin element, Pad pad, Caps caps);
  }

  public void connect(final AUTOPLUG_FACTORIES listener) {
    connect(AUTOPLUG_FACTORIES.class, listener, new GstCallback() {
      public GValueArray callback(DecodeBin elem, Pad pad, Caps caps) {
        Optional<List<ElementFactory>> factories = listener.autoplugFactories(elem, pad, caps);
        return factories
            .map(list -> {
              GValueArray array = new GValueArray(list.size(), false);
              list.forEach(factory -> array.append(new GValueAPI.GValue(GType.OBJECT, factory)));
              return array;
            })
            .orElse(null);
      }
    });
  }

  public void disconnect(AUTOPLUG_FACTORIES listener) {
    disconnect(AUTOPLUG_FACTORIES.class, listener);
  }

  public static interface AUTOPLUG_SORT {
    public Optional<List<ElementFactory>> autoplugSort(DecodeBin element, Pad pad, Caps caps, List<ElementFactory> factories);
  }

  public void connect(final AUTOPLUG_SORT listener) {
    connect(AUTOPLUG_SORT.class, listener, new GstCallback() {
      public GValueArray callback(DecodeBin elem, Pad pad, Caps caps, GValueArray factories) {
        int size = factories.getNValues();
        List<ElementFactory> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
          Object factory = factories.getValue(i);
          if (factory instanceof ElementFactory) {
            list.add((ElementFactory) factory);
          }
        }
        Optional<List<ElementFactory>> response = listener.autoplugSort(elem, pad, caps, list);
        return response
            .map(l -> {
              GValueArray array = new GValueArray(l.size(), false);
              list.forEach(factory -> array.append(new GValueAPI.GValue(GType.OBJECT, factory)));
              return array;
            })
            .orElse(null);
      }
    });
  }

  public void disconnect(AUTOPLUG_SORT listener) {
    disconnect(AUTOPLUG_SORT.class, listener);
  }

  public enum AutoplugSelectResult implements NativeEnum<AutoplugSelectResult> {
    TRY(0),
    EXPOSE(1),
    SKIP(2);

    AutoplugSelectResult(final int value) {
      this.value = value;
    }

    @Override
    public int intValue() {
      return value;
    }

    private final int value;
  }

  public static interface AUTOPLUG_SELECT {
    public AutoplugSelectResult autoplugSelect(final DecodeBin element, final Pad pad, final Caps caps, final ElementFactory factory);
  }

  public void connect(final AUTOPLUG_SELECT listener) {
    connect(AUTOPLUG_SELECT.class, listener, new GstCallback() {
      public AutoplugSelectResult callback(final DecodeBin elem, final Pad pad, final Caps caps, final ElementFactory factory) {
        return listener.autoplugSelect(elem, pad, caps, factory);
      }
    });
  }

  public void disconnect(final AUTOPLUG_SELECT listener) {
    disconnect(AUTOPLUG_SELECT.class, listener);
  }

  public static interface AUTOPLUG_QUERY {
    public boolean autoplugQuery(DecodeBin element, Pad pad, Element child, Query query);
  }

  public void connect(final AUTOPLUG_QUERY listener) {
    connect(AUTOPLUG_QUERY.class, listener, new GstCallback() {
      public boolean callback(DecodeBin elem, Pad pad, Element child, Query query) {
        return listener.autoplugQuery(elem, pad, child, query);
      }
    });
  }

  public void disconnect(AUTOPLUG_QUERY listener) {
    disconnect(AUTOPLUG_QUERY.class, listener);
  }

  public static interface DRAINED {
    public void drained(DecodeBin element);
  }

  public void connect(final DRAINED listener) {
    connect(DRAINED.class, listener, new GstCallback() {
      public void callback(DecodeBin elem) {
        listener.drained(elem);
      }
    });
  }

  public void disconnect(DRAINED listener) {
    disconnect(DRAINED.class, listener);
  }
}
