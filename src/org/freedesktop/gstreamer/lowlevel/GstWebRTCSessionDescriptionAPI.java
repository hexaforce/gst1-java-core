package org.freedesktop.gstreamer.lowlevel;

import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
import org.freedesktop.gstreamer.SDPMessage;
import org.freedesktop.gstreamer.lowlevel.annotations.CallerOwnsReturn;
import org.freedesktop.gstreamer.webrtc.WebRTCSDPType;
import org.freedesktop.gstreamer.webrtc.WebRTCSessionDescription;

public interface GstWebRTCSessionDescriptionAPI extends com.sun.jna.Library {
  GstWebRTCSessionDescriptionAPI GSTWEBRTCSESSIONDESCRIPTION_API = GstNative.load("gstwebrtc", GstWebRTCSessionDescriptionAPI.class);

  public static final class WebRTCSessionDescriptionStruct extends com.sun.jna.Structure {
    public volatile WebRTCSDPType type;
    public volatile SDPMessage sdp;

    public WebRTCSessionDescriptionStruct(final Pointer ptr) {
      useMemory(ptr);
    }

    @Override
    protected List<String> getFieldOrder() {
      return Arrays.asList(new String[] {"type", "sdp"});
    }
  }

  GType gst_webrtc_session_description_get_type();

  @CallerOwnsReturn WebRTCSessionDescription gst_webrtc_session_description_new(WebRTCSDPType type, SDPMessage sdp);

  @CallerOwnsReturn Pointer ptr_gst_webrtc_session_description_new(WebRTCSDPType type, SDPMessage sdp);

  @CallerOwnsReturn WebRTCSessionDescription gst_webrtc_session_description_copy(WebRTCSessionDescription src);

  @CallerOwnsReturn Pointer ptr_gst_webrtc_session_description_copy(WebRTCSessionDescription src);

  void gst_webrtc_session_description_free(Pointer desc);
}
