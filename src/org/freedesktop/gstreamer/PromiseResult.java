package org.freedesktop.gstreamer;

import org.freedesktop.gstreamer.lowlevel.annotations.DefaultEnumValue;

@Gst.Since(minor = 14) public enum PromiseResult { @DefaultEnumValue PENDING, INTERRUPTED, REPLIED, EXPIRED }
