package eventloop.command.util;

import eventloop.event.StorageableWithTimeout;
import java.util.HashMap;

public final class TestStorageableWithTimeout
    implements StorageableWithTimeout {
  private HashMap<String, String> storage;

  public TestStorageableWithTimeout() { this.storage = new HashMap<>(); }

  public void addValueToStorage(String key, String value) {
    this.storage.put(key, value);
  }

  public String getValueFromStorage(String key) {
    return this.storage.get(key);
  }

  public void deleteKeyFromStorageable(String key) { this.storage.remove(key); }
  public void deleteKeyWithTimeout(String key, int timeout) {
    Runnable deleteKey = () -> {
      try {
        Thread.sleep(timeout);
        this.deleteKeyFromStorageable(key);
      } catch (InterruptedException e) {
        System.out.println(e);
      }
    };
    Thread thread = new Thread(deleteKey);
    thread.start();
  }
}
