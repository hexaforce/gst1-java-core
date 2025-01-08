package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import org.freedesktop.gstreamer.MiniObject;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstEventAPI;
import org.freedesktop.gstreamer.lowlevel.ReferenceManager;
import org.freedesktop.gstreamer.lowlevel.annotations.HasSubtype;

@HasSubtype
public class Event extends MiniObject {
  public static final String GTYPE_NAME = "GstEvent";
  private static final Map<EventType, Function<Initializer, Event>> TYPE_MAP = new EnumMap<>(EventType.class);

  static {
    TYPE_MAP.put(EventType.BUFFERSIZE, BufferSizeEvent::new);
    TYPE_MAP.put(EventType.EOS, EOSEvent::new);
    TYPE_MAP.put(EventType.CAPS, CapsEvent::new);
    TYPE_MAP.put(EventType.RECONFIGURE, ReconfigureEvent::new);
    TYPE_MAP.put(EventType.STREAM_START, StreamStartEvent::new);
    TYPE_MAP.put(EventType.LATENCY, LatencyEvent::new);
    TYPE_MAP.put(EventType.FLUSH_START, FlushStartEvent::new);
    TYPE_MAP.put(EventType.FLUSH_STOP, FlushStopEvent::new);
    TYPE_MAP.put(EventType.NAVIGATION, NavigationEvent::new);
    TYPE_MAP.put(EventType.SEGMENT, SegmentEvent::new);
    TYPE_MAP.put(EventType.SEEK, SeekEvent::new);
    TYPE_MAP.put(EventType.TAG, TagEvent::new);
    TYPE_MAP.put(EventType.QOS, QOSEvent::new);
    TYPE_MAP.put(EventType.STEP, StepEvent::new);
  }

  Event(Initializer init) {
    super(init);
  }

  public Structure getStructure() {
    return ReferenceManager.addKeepAliveReference(GSTEVENT_API.gst_event_get_structure(this), this);
  }

  private static Event create(Initializer init) {
    GstEventAPI.EventStruct struct = new GstEventAPI.EventStruct(init.ptr.getPointer());
    EventType type = (EventType) struct.readField("type");
    return TYPE_MAP.getOrDefault(type, Event::new).apply(init);
  }

  public static class Types implements TypeProvider {
    @Override
    public Stream<TypeRegistration<?>> types() {
      return Stream.of(Natives.registration(Event.class, GTYPE_NAME, Event::create));
    }
  }
}
