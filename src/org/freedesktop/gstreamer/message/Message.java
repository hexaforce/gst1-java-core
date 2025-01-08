package org.freedesktop.gstreamer.message;

import static org.freedesktop.gstreamer.lowlevel.GstMessageAPI.GSTMESSAGE_API;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.MiniObject;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.glib.Natives;
import org.freedesktop.gstreamer.lowlevel.GstMessagePtr;
import org.freedesktop.gstreamer.lowlevel.ReferenceManager;
import org.freedesktop.gstreamer.lowlevel.annotations.HasSubtype;

@HasSubtype
public class Message extends MiniObject {
  public static final String GTYPE_NAME = "GstMessage";
  private static final Map<MessageType, Function<Initializer, Message>> TYPE_MAP = new EnumMap<>(MessageType.class);

  static {
    TYPE_MAP.put(MessageType.EOS, EOSMessage::new);
    TYPE_MAP.put(MessageType.ERROR, ErrorMessage::new);
    TYPE_MAP.put(MessageType.BUFFERING, BufferingMessage::new);
    TYPE_MAP.put(MessageType.DURATION_CHANGED, DurationChangedMessage::new);
    TYPE_MAP.put(MessageType.INFO, InfoMessage::new);
    TYPE_MAP.put(MessageType.LATENCY, LatencyMessage::new);
    TYPE_MAP.put(MessageType.SEGMENT_DONE, SegmentDoneMessage::new);
    TYPE_MAP.put(MessageType.STATE_CHANGED, StateChangedMessage::new);
    TYPE_MAP.put(MessageType.TAG, TagMessage::new);
    TYPE_MAP.put(MessageType.WARNING, WarningMessage::new);
    TYPE_MAP.put(MessageType.NEED_CONTEXT, NeedContextMessage::new);
  }

  private final Handle handle;

  Message(Initializer init) {
    this(new Handle(init.ptr.as(GstMessagePtr.class, GstMessagePtr::new), init.ownsHandle), init.needRef);
  }

  Message(Handle handle, boolean needRef) {
    super(handle, needRef);
    this.handle = handle;
  }

  public GstObject getSource() {
    return Natives.objectFor(handle.getPointer().getSource(), GstObject.class, true, true);
  }

  public Structure getStructure() {
    return ReferenceManager.addKeepAliveReference(GSTMESSAGE_API.gst_message_get_structure(this), this);
  }

  public MessageType getType() {
    return NativeEnum.fromInt(MessageType.class, MessageType.UNKNOWN, handle.getPointer().getMessageType());
  }

  private static Message create(Initializer init) {
    MessageType type = NativeEnum.fromInt(MessageType.class, MessageType.UNKNOWN, init.ptr.as(GstMessagePtr.class, GstMessagePtr::new).getMessageType());
    return TYPE_MAP.getOrDefault(type, Message::new).apply(init);
  }

  public static class Types implements TypeProvider {
    @Override
    public Stream<TypeRegistration<?>> types() {
      return Stream.of(Natives.registration(Message.class, GTYPE_NAME, Message::create));
    }
  }

  static class Handle extends MiniObject.Handle {
    Handle(GstMessagePtr ptr, boolean ownsHandle) {
      super(ptr, ownsHandle);
    }

    @Override
    protected GstMessagePtr getPointer() {
      return (GstMessagePtr) super.getPointer();
    }
  }
}
