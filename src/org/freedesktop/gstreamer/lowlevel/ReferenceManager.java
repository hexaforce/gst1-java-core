package org.freedesktop.gstreamer.lowlevel;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.freedesktop.gstreamer.glib.RefCountedObject;

public class ReferenceManager {
  private ReferenceManager() {}

  public static <T extends Object> T addKeepAliveReference(T ref, RefCountedObject target) {
    if (ref != null) {
      StaticData.map.put(new WeakReference<Object>(ref, StaticData.queue), target);
    }
    return ref;
  }

  private static class StaticData {
    private static final Map<Object, Object> map = new ConcurrentHashMap<Object, Object>();
    private static final ReferenceQueue<Object> queue = new ReferenceQueue<Object>();

    static {
      Thread t = new Thread(new Runnable() {
        public void run() {
          while (true) {
            try {
              map.remove(queue.remove());
            } catch (InterruptedException ex) {
              break;
            } catch (Throwable ex) {
              continue;
            }
          }
        }
      });
      t.setName("Reference reaper");
      t.setDaemon(true);
      t.start();
    }
  }
}
