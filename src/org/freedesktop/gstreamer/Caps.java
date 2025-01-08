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

import static org.freedesktop.gstreamer.lowlevel.GstCapsAPI.GSTCAPS_API;

import org.freedesktop.gstreamer.glib.Natives;

@SuppressWarnings("unchecked")
public class Caps extends MiniObject {
  public static final String GTYPE_NAME = "GstCaps";

  public Caps() {
    this(Natives.initializer(GSTCAPS_API.ptr_gst_caps_new_empty()));
  }

  public Caps(String caps) {
    this(Natives.initializer(GSTCAPS_API.ptr_gst_caps_from_string(caps)));
  }

  public Caps(Caps caps) {
    this(Natives.initializer(GSTCAPS_API.ptr_gst_caps_copy(caps)));
  }

  Caps(Initializer init) {
    super(init);
  }

  public void append(Caps caps) {
    GSTCAPS_API.gst_caps_append(this, caps);
  }

  public void append(Structure struct) {
    GSTCAPS_API.gst_caps_append_structure(this, struct);
  }

  public Caps copy() {
    return GSTCAPS_API.gst_caps_copy(this);
  }

  @Override
  public boolean equals(Object other) {
    if (other == null || !(other instanceof Caps)) {
      return false;
    }
    return other == this || isEqual((Caps) other);
  }

  public Structure getStructure(int index) {
    return GSTCAPS_API.gst_caps_get_structure(this, index);
  }

  public Caps intersect(Caps other) {
    return GSTCAPS_API.gst_caps_intersect(this, other);
  }

  public boolean isAlwaysCompatible(Caps other) {
    return GSTCAPS_API.gst_caps_is_always_compatible(this, other);
  }

  public boolean isAny() {
    return GSTCAPS_API.gst_caps_is_any(this);
  }

  public boolean isEmpty() {
    return GSTCAPS_API.gst_caps_is_empty(this);
  }

  public boolean isEqual(Caps other) {
    return GSTCAPS_API.gst_caps_is_equal(this, other);
  }

  public boolean isEqualFixed(Caps other) {
    return GSTCAPS_API.gst_caps_is_equal_fixed(this, other);
  }

  public boolean isFixed() {
    return GSTCAPS_API.gst_caps_is_fixed(this);
  }

  public boolean isSubset(Caps superset) {
    return GSTCAPS_API.gst_caps_is_subset(this, superset);
  }

  public Caps makeWritable() {
    return GSTCAPS_API.gst_caps_make_writable(this);
  }

  public Caps normalize() {
    Natives.ref(this);
    return GSTCAPS_API.gst_caps_normalize(this);
  }

  public void removeStructure(int index) {
    GSTCAPS_API.gst_caps_remove_structure(this, index);
  }

  public void setInteger(String field, Integer value) {
    GSTCAPS_API.gst_caps_set_simple(this, field, value, null);
  }

  public Caps simplify() {
    Natives.ref(this);
    return GSTCAPS_API.gst_caps_simplify(this);
  }

  public int size() {
    return GSTCAPS_API.gst_caps_get_size(this);
  }

  public Caps subtract(Caps subtrahend) {
    return GSTCAPS_API.gst_caps_subtract(this, subtrahend);
  }

  @Override
  public String toString() {
    return GSTCAPS_API.gst_caps_to_string(this);
  }

  public Caps truncate() {
    Natives.ref(this);
    return GSTCAPS_API.gst_caps_truncate(this);
  }

  public static Caps anyCaps() {
    return new Caps(Natives.initializer(GSTCAPS_API.ptr_gst_caps_new_any()));
  }

  public static Caps emptyCaps() {
    return new Caps(Natives.initializer(GSTCAPS_API.ptr_gst_caps_new_empty()));
  }

  public static Caps fromString(String caps) {
    return new Caps(Natives.initializer(GSTCAPS_API.ptr_gst_caps_from_string(caps)));
  }

  public static Caps merge(Caps caps1, Caps caps2) {
    return GSTCAPS_API.gst_caps_merge(caps1, caps2);
  }
}
