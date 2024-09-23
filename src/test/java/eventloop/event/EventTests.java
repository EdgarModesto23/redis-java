package eventloop.event;

import static org.junit.jupiter.api.Assertions.assertTrue;

import eventloop.command.AbstractCommand;
import eventloop.command.PingFactory;
import eventloop.command.SetFactory;
import eventloop.command.StorageCommand;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;

public class EventTests {

  public static class TestEventLoop implements EventLoopResults {
    private Queue<FinishedEvent> resultsQueue = new LinkedList<>();

    public void pushResultEvent(FinishedEvent event) {
      this.resultsQueue.add(event);
    }
  }

  public static Storageable storage = new Storageable() {
    private HashMap<String, String> storage = new HashMap<>() {};

    public String getValueFromStorage(String key) {
      return this.storage.get(key);
    }

    public void addValueToStorage(String key, String value) {
      this.storage.put(key, value);
    }
  };

  @Test
  public void testBaseEvent() {
    AbstractCommand command = new PingFactory().createCommand();
    AbstractEvent event = new EventFactory().createEvent(command, storage);

    assertTrue(event instanceof BaseEvent);
  }

  @Test
  public void testStorageEvent() {
    StorageCommand storageCommand = new SetFactory().createCommand();
    AbstractEvent event =
        new EventFactory().createEvent(storageCommand, storage);

    assertTrue(event instanceof StorageEvent);
  }
}
