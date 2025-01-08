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

package org.freedesktop.gstreamer.interfaces;

import static org.freedesktop.gstreamer.lowlevel.GstVideoOrientationAPI.GSTVIDEOORIENTATION_API;

import org.freedesktop.gstreamer.Element;

public class VideoOrientation extends GstInterface {
  public static final VideoOrientation wrap(Element element) {
    return new VideoOrientation(element);
  }

  private VideoOrientation(Element element) {
    super(element, GSTVIDEOORIENTATION_API.gst_video_orientation_get_type());
  }

  public boolean setHflip(boolean flip) {
    return GSTVIDEOORIENTATION_API.gst_video_orientation_set_hflip(this, flip);
  }

  public boolean setVflip(boolean flip) {
    return GSTVIDEOORIENTATION_API.gst_video_orientation_set_vflip(this, flip);
  }

  public boolean setHcenter(int center) {
    return GSTVIDEOORIENTATION_API.gst_video_orientation_set_hcenter(this, center);
  }

  public boolean setVcenter(int center) {
    return GSTVIDEOORIENTATION_API.gst_video_orientation_set_vcenter(this, center);
  }
}
