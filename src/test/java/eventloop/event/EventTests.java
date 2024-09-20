package eventloop.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import eventloop.command.AbstractCommand;
import eventloop.command.PingFactory;
import java.nio.channels.SocketChannel;
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

  @Test
  public void testRunCommand() {
    SocketChannel client = Mockito.mock(SocketChannel.class);
    TestEventLoop eventloop = new TestEventLoop();
    AbstractCommand command = new PingFactory().createCommand();
    command.setEventloop(eventloop);
    command.setClient(client);
    command.run();

    assertEquals(eventloop.resultsQueue.remove().getResult(), "+PONG\r\n");
  }
}
