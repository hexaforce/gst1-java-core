package org.freedesktop.gstreamer.event;

import static org.freedesktop.gstreamer.lowlevel.GstEventAPI.GSTEVENT_API;

import org.freedesktop.gstreamer.glib.Natives;

public class QOSEvent extends Event {
  QOSEvent(Initializer init) {
    super(init);
  }

  public QOSEvent(QOSType type, double proportion, long difference, long timestamp) {
    super(Natives.initializer(GSTEVENT_API.ptr_gst_event_new_qos(type, proportion, difference, timestamp)));
  }

  public QOSType getType() {
    QOSType[] type = {null};
    GSTEVENT_API.gst_event_parse_qos(this, type, null, null, (long[]) null);
    return type[0];
  }

  public double getProportion() {
    double[] p = {0d};
    GSTEVENT_API.gst_event_parse_qos(this, null, p, null, (long[]) null);
    return p[0];
  }

  public long getDifference() {
    long[] diff = {0};
    GSTEVENT_API.gst_event_parse_qos(this, null, null, diff, (long[]) null);
    return diff[0];
  }

  public long getTimestamp() {
    long[] timestamp = new long[1];
    GSTEVENT_API.gst_event_parse_qos(this, null, null, null, timestamp);
    return timestamp[0];
  }
}
