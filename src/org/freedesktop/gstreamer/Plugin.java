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

import static org.freedesktop.gstreamer.lowlevel.GstPluginAPI.GSTPLUGIN_API;

public class Plugin extends GstObject {
  public static final String GTYPE_NAME = "GstPlugin";

  Plugin(Initializer init) {
    super(init);
  }

  public static Plugin loadByName(String pluginName) {
    return GSTPLUGIN_API.gst_plugin_load_by_name(pluginName);
  }

  @Override
  public String getName() {
    return GSTPLUGIN_API.gst_plugin_get_name(this);
  }

  public String getDescription() {
    return GSTPLUGIN_API.gst_plugin_get_description(this);
  }

  public String getFilename() {
    return GSTPLUGIN_API.gst_plugin_get_filename(this);
  }

  public String getVersion() {
    return GSTPLUGIN_API.gst_plugin_get_version(this);
  }

  public String getLicense() {
    return GSTPLUGIN_API.gst_plugin_get_license(this);
  }

  public String getSource() {
    return GSTPLUGIN_API.gst_plugin_get_source(this);
  }

  public String getPackage() {
    return GSTPLUGIN_API.gst_plugin_get_package(this);
  }

  public String getOrigin() {
    return GSTPLUGIN_API.gst_plugin_get_origin(this);
  }

  public String getReleaseDateString() {
    return GSTPLUGIN_API.gst_plugin_get_release_date_string(this);
  }

  public boolean isLoaded() {
    return GSTPLUGIN_API.gst_plugin_is_loaded(this);
  }
}
