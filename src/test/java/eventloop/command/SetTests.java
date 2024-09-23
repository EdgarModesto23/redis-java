package eventloop.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import eventloop.command.util.TestStorageableWithTimeout;
import eventloop.event.util.TestEventLoopResults;
import java.nio.channels.SocketChannel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SetTests {

  @Test
  public void testSetWithTimeout() {
    TestEventLoopResults eventloop = new TestEventLoopResults();
    TestStorageableWithTimeout storage = new TestStorageableWithTimeout();
    SocketChannel client = Mockito.mock(SocketChannel.class);

    SetWithTimeout command = new SetWithTimeout(100);
    command.setClient(client);
    command.setEventloop(eventloop);
    command.setStorageable(storage);
    command.setkey("Foo");
    command.setValue("Bar");
    command.run();

    assertEquals("Bar", storage.getValueFromStorage("Foo"));
    assertEquals("+OK\r\n", command.toCLRF());

    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      fail(e.toString());
    }

    assertEquals(null, storage.getValueFromStorage("Foo"));
  }

  @Test
  public void testCorrectOutput() {
    TestEventLoopResults eventloop = new TestEventLoopResults();
    TestStorageableWithTimeout storage = new TestStorageableWithTimeout();
    SocketChannel client = Mockito.mock(SocketChannel.class);

    Set command = new Set();
    command.setClient(client);
    command.setEventloop(eventloop);
    command.setStorageable(storage);
    command.setkey("Foo");
    command.setValue("Bar");
    command.run();

    assertEquals("Bar", command.getValue());
    assertEquals("Foo", command.getkey());
    assertEquals(command.getClient(), client);
    assertEquals(command.getEventloop(), eventloop);
    assertEquals("+OK\r\n", eventloop.resultsQueue.remove().getResult());

    command.setValue("Foobar");
    command.run();

    assertEquals("Foobar", command.getValue());
    assertEquals("Foobar", storage.getValueFromStorage("Foo"));
    assertEquals("+OK\r\n", eventloop.resultsQueue.remove().getResult());
  }
}
