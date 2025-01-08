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

import static org.freedesktop.gstreamer.lowlevel.GstValueAPI.GSTVALUE_API;

import org.freedesktop.gstreamer.lowlevel.GValueAPI;

public class Range {
  private GValueAPI.GValue value;

  Range(GValueAPI.GValue value) {
    this.value = value;
  }

  public Fraction getMinFraction() {
    GValueAPI.GValue frMin = GSTVALUE_API.gst_value_get_fraction_range_min(value);
    int num = GSTVALUE_API.gst_value_get_fraction_numerator(frMin);
    int denom = GSTVALUE_API.gst_value_get_fraction_denominator(frMin);
    return new Fraction(num, denom);
  }

  public Fraction getMaxFraction() {
    GValueAPI.GValue frMax = GSTVALUE_API.gst_value_get_fraction_range_max(value);
    int num = GSTVALUE_API.gst_value_get_fraction_numerator(frMax);
    int denom = GSTVALUE_API.gst_value_get_fraction_denominator(frMax);
    return new Fraction(num, denom);
  }

  public double getMinDouble() {
    return GSTVALUE_API.gst_value_get_double_range_min(value);
  }

  public double getMaxDouble() {
    return GSTVALUE_API.gst_value_get_double_range_max(value);
  }

  public int getMinInt() {
    return GSTVALUE_API.gst_value_get_int_range_min(value);
  }

  public int getMaxInt() {
    return GSTVALUE_API.gst_value_get_int_range_max(value);
  }
}
