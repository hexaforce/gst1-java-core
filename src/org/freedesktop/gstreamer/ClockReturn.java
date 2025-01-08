package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;

public enum ClockReturn { OK, EARLY, UNSCHEDULED, BUSY, BADTIME, ERROR, @DefaultEnumValue UNSUPPORTED, DONE }
