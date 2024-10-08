package eventloop.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import eventloop.event.EventLoopResults;
import eventloop.event.FinishedEvent;
import eventloop.event.config.Config;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PingTests {

  public static class TestEventLoop implements EventLoopResults {
    private Config config;
    private Queue<FinishedEvent> resultsQueue = new LinkedList<>();
    public void setConfig(Config config) { this.config = config; }

    public Config getConfig() { return this.config; }

    public void pushResultEvent(FinishedEvent event) {
      this.resultsQueue.add(event);
    }
  }

  @Test
  public void testCorrectOutput() {
    TestEventLoop eventloop = new TestEventLoop();
    SocketChannel client = Mockito.mock(SocketChannel.class);

    Ping command = new Ping();
    command.setClient(client);
    command.setEventloop(eventloop);
    command.run();

    assertEquals("+PONG\r\n", eventloop.resultsQueue.remove().getResult());
  }
}
