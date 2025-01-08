package org.freedesktop.gstreamer.webrtc;

import org.freedesktop.gstreamer.Bin;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Promise;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.glib.NativeEnum;
import org.freedesktop.gstreamer.lowlevel.GstAPI.GstCallback;

@SuppressWarnings("unused")
@Gst.Since(minor = 14)
public class WebRTCBin extends Bin {
  public static final String GST_NAME = "webrtcbin";
  public static final String GTYPE_NAME = "GstWebRTCBin";

  WebRTCBin(Initializer init) {
    super(init);
  }

  public WebRTCBin(String name) {
    super(makeRawElement(GST_NAME, name));
  }

  public static interface ON_NEGOTIATION_NEEDED {
    public void onNegotiationNeeded(Element elem);
  }

  public static interface ON_ICE_CANDIDATE {
    public void onIceCandidate(int sdpMLineIndex, String candidate);
  }

  public static interface CREATE_OFFER {
    public void onOfferCreated(WebRTCSessionDescription offer);
  }

  public static interface CREATE_ANSWER {
    public void onAnswerCreated(WebRTCSessionDescription answer);
  }

  public void connect(final ON_NEGOTIATION_NEEDED listener) {
    connect(ON_NEGOTIATION_NEEDED.class, listener, new GstCallback() {
      public void callback(Element elem) {
        listener.onNegotiationNeeded(elem);
      }
    });
  }

  public void connect(final ON_ICE_CANDIDATE listener) {
    connect(ON_ICE_CANDIDATE.class, listener, new GstCallback() {
      public void callback(Element elem, int sdpMLineIndex, String candidate) {
        listener.onIceCandidate(sdpMLineIndex, candidate);
      }
    });
  }

  public void createOffer(final CREATE_OFFER listener) {
    Promise promise = new Promise(new Promise.PROMISE_CHANGE() {
      public void onChange(Promise promise) {
        Structure reply = promise.getReply();
        WebRTCSessionDescription description = (WebRTCSessionDescription) reply.getValue("offer");
        listener.onOfferCreated(description);
        promise.dispose();
      }
    });
    emit("create-offer", null, promise);
  }

  public void createAnswer(final CREATE_ANSWER listener) {
    Promise promise = new Promise(new Promise.PROMISE_CHANGE() {
      public void onChange(Promise promise) {
        Structure reply = promise.getReply();
        WebRTCSessionDescription description = (WebRTCSessionDescription) reply.getValue("answer");
        listener.onAnswerCreated(description);
        promise.dispose();
      }
    });
    emit("create-answer", null, promise);
  }

  public void addIceCandidate(int sdpMLineIndex, String candidate) {
    emit("add-ice-candidate", sdpMLineIndex, candidate);
  }

  public void setLocalDescription(WebRTCSessionDescription description) {
    Promise promise = new Promise();
    description.disown();
    emit("set-local-description", description, promise);
    promise.interrupt();
    promise.dispose();
  }

  public void setRemoteDescription(WebRTCSessionDescription description) {
    Promise promise = new Promise();
    description.disown();
    emit("set-remote-description", description, promise);
    promise.interrupt();
    promise.dispose();
  }

  public void setStunServer(String server) {
    set("stun-server", server);
  }

  public String getStunServer() {
    return (String) get("stun-server");
  }

  public void setTurnServer(String server) {
    set("turn-server", server);
  }

  public String getTurnServer() {
    return (String) get("turn-server");
  }

  public void setBundlePolicy(WebRTCBundlePolicy value) {
    set("bundle-policy", value.intValue());
  }

  public WebRTCBundlePolicy getBundlePolicy() {
    return NativeEnum.fromInt(WebRTCBundlePolicy.class, (Integer) get("bundle-policy"));
  }

  public WebRTCPeerConnectionState getConnectionState() {
    return NativeEnum.fromInt(WebRTCPeerConnectionState.class, (Integer) get("connection-state"));
  }

  public WebRTCICEGatheringState getICEGatheringState() {
    return NativeEnum.fromInt(WebRTCICEGatheringState.class, (Integer) get("ice-gathering-state"));
  }

  public WebRTCSessionDescription getLocalDescription() {
    WebRTCSessionDescription description = (WebRTCSessionDescription) get("local-description");
    description.disown();
    return description;
  }

  public WebRTCSessionDescription getRemoteDescription() {
    WebRTCSessionDescription description = (WebRTCSessionDescription) get("remote-description");
    description.disown();
    return description;
  }
}
