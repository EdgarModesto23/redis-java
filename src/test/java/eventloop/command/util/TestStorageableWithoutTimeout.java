package eventloop.command.util;

import eventloop.event.Storageable;
import java.util.HashMap;
import java.util.Map;

public final class TestStorageableWithoutTimeout implements Storageable {
  private HashMap<String, String> storage;

  public TestStorageableWithoutTimeout() {
    this.storage = new HashMap<>(Map.of("Foo", "Bar"));
  }

  public void addValueToStorage(String key, String value) {
    this.storage.put(key, value);
  }

  public String getValueFromStorage(String key) {
    return this.storage.get(key);
  }
}
