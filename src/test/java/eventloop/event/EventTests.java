package eventloop.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import eventloop.command.AbstractCommand;
import eventloop.command.PingFactory;
import eventloop.command.SetFactory;
import eventloop.command.StorageCommand;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EventTests {

  public class TestEventLoop implements EventLoopResults {
    private Queue<FinishedEvent> resultsQueue;

    public TestEventLoop() { this.resultsQueue = new LinkedList<>(); }

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
  public void testStorageable() {
    storage.addValueToStorage("Foo", "Bar");

    assertEquals("Bar", storage.getValueFromStorage("Foo"));
  }

  @Test
  public void testRunCommand() {
    SocketChannel client = Mockito.mock(SocketChannel.class);
    TestEventLoop eventloop = new TestEventLoop();
    AbstractCommand command = new PingFactory().createCommand();
    command.setEventloop(eventloop);
    command.setClient(client);
    command.run();

    assertEquals(eventloop.resultsQueue.remove().getResult(), "+PONG\r\n");

    StorageCommand storageCommand = new SetFactory().createCommand();
    storageCommand.setEventloop(eventloop);
    storageCommand.setClient(client);
    storageCommand.setStorageable(storage);
    storageCommand.setkey("Foo");
    storageCommand.setValue("Bar");
    command.run();

    assertEquals(storageCommand.getValue(), "Bar");
  }
}
