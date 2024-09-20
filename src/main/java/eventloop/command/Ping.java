package eventloop.command;

import eventloop.event.EventLoopResults;
import eventloop.event.FinishedEvent;
import java.nio.channels.SocketChannel;

public class Ping extends AbstractCommand {

  public Ping() {
    this.eventloop = null;
    this.client = null;
  }

  public Ping(EventLoopResults eventloop, SocketChannel client) {
    this.eventloop = eventloop;
    this.client = client;
  }

  @Override
  public String toCLRF() {
    return "+PONG\r\n";
  }
  @Override
  public void run() {
    String result = this.toCLRF();
    FinishedEvent resultEvent = new FinishedEvent(result, this.client);
    this.eventloop.pushResultEvent(resultEvent);
  };
}
