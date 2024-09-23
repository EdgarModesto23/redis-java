package eventloop.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import eventloop.command.util.TestStorageableWithTimeout;
import eventloop.command.util.TestStorageableWithoutTimeout;
import eventloop.event.util.TestEventLoopResults;
import java.nio.channels.SocketChannel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetTests {

  @Test
  public void testGetKeyWithTimeout() {
    TestEventLoopResults eventloop = new TestEventLoopResults();
    TestStorageableWithTimeout storage = new TestStorageableWithTimeout();
    SocketChannel client = Mockito.mock(SocketChannel.class);

    SetWithTimeout command = new SetWithTimeout(300);
    command.setClient(client);
    command.setEventloop(eventloop);
    command.setStorageable(storage);
    command.setkey("Foo");
    command.setValue("Bar");
    command.run();

    assertEquals("Bar", storage.getValueFromStorage("Foo"));
    assertEquals("+OK\r\n", command.toCLRF());
    Get getCommand = new Get();
    getCommand.setStorageable(storage);
    getCommand.setEventloop(eventloop);
    getCommand.setClient(client);
    getCommand.setkey("Foo");
    getCommand.run();

    assertEquals("$3\r\nBar\r\n", getCommand.toCLRF());

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      fail(e.toString());
    }

    getCommand.run();

    assertEquals(null, storage.getValueFromStorage("Foo"));
    assertEquals("$-1\r\n", getCommand.toCLRF());
  }

  @Test
  public void testEmptyKey() {
    TestEventLoopResults eventloop = new TestEventLoopResults();
    TestStorageableWithoutTimeout storage = new TestStorageableWithoutTimeout();
    SocketChannel client = Mockito.mock(SocketChannel.class);

    Get command = new Get();
    command.setStorageable(storage);
    command.setEventloop(eventloop);
    command.setClient(client);
    command.setkey("Bar");
    command.run();

    assertEquals("$-1\r\n", eventloop.resultsQueue.remove().getResult());
  }

  @Test
  public void testCorrectOutput() {
    TestEventLoopResults eventloop = new TestEventLoopResults();
    TestStorageableWithoutTimeout storage = new TestStorageableWithoutTimeout();
    SocketChannel client = Mockito.mock(SocketChannel.class);

    Get command = new Get();
    command.setStorageable(storage);
    command.setEventloop(eventloop);
    command.setClient(client);
    command.setkey("Foo");
    command.run();

    assertEquals("$3\r\nBar\r\n", eventloop.resultsQueue.remove().getResult());
  }
}
