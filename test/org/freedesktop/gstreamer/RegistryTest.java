package org.freedesktop.gstreamer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RegistryTest {
  public RegistryTest() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    Gst.init("RegistryTest", new String[] {});
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Gst.deinit();
  }

  @Test
  public void testGet() {
    Registry registry = Registry.get();
    assertNotNull("Registry.getDefault() returned null", registry);
  }

  @Test
  public void testGetPluginList() {
    final String PLUGIN = "vorbis";
    Registry registry = Registry.get();
    List<Plugin> plugins = registry.getPluginList();
    assertFalse("No plugins found", plugins.isEmpty());
    boolean pluginFound = false;
    for (Plugin p : plugins) {
      if (p.getName().equals(PLUGIN)) {
        pluginFound = true;
      }
    }
    assertTrue(PLUGIN + " plugin not found", pluginFound);
  }

  @Test
  public void testGetPluginListFiltered() {
    final String PLUGIN = "vorbis";
    Registry registry = Registry.get();
    final boolean[] filterCalled = {false};
    List<Plugin> plugins = registry.getPluginList(new Registry.PluginFilter() {
      @Override
      public boolean accept(Plugin plugin) {
        filterCalled[0] = true;
        return plugin.getName().equals(PLUGIN);
      }
    });
    assertFalse("No plugins found", plugins.isEmpty());
    assertTrue("PluginFilter not called", filterCalled[0]);
    assertEquals("Plugin list should contain 1 item", 1, plugins.size());
    assertEquals(PLUGIN + " plugin not found", PLUGIN, plugins.get(0).getName());
  }

  @Test
  public void testGetPluginFeatureListByPlugin() {
    final String PLUGIN = "vorbis";
    final String FEATURE = "vorbisdec";
    Registry registry = Registry.get();
    List<PluginFeature> features = registry.getPluginFeatureListByPlugin(PLUGIN);
    assertFalse("No plugin features found", features.isEmpty());
    boolean pluginFound = false;
    for (PluginFeature p : features) {
      if (p.getName().equals(FEATURE)) {
        pluginFound = true;
      }
    }
    assertTrue(PLUGIN + " plugin not found", pluginFound);
  }

  @Test
  public void testFindPluginFeature() {
    PluginFeature f = Registry.get().lookupFeature("decodebin");
    assertNotNull(f);
  }

  @Test
  public void testFindPlugin() {
    Plugin f = Registry.get().findPlugin("playback");
    assertNotNull(f);
  }
}
