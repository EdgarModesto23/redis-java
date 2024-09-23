package eventloop.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import eventloop.command.Set;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

public class EventFactoryTests {
  private Storageable storage = new Storageable() {
    private HashMap<String, String> storage;
    public String getValueFromStorage(String key) {
      return this.storage.get(key);
    }

    public void addValueToStorage(String key, String value) {
      this.storage.put(key, value);
    }
  };

  @Test
  public void testGetStorageEvent() {
    Set command = new Set();
    AbstractEvent event = new EventFactory().createEvent(command, this.storage);
    assertTrue(event instanceof StorageEvent);
  }

  @Test
  public void testRunAbstractCommand() {
    Set command = new Set();
    AbstractEvent event = new EventFactory().createEvent(command, this.storage);
    event.RunAbstractCommand();

    assertEquals(command.getStorageable(), this.storage);
  }
}
