package eventloop.command;

import eventloop.event.EventLoopResults;
import java.nio.channels.SocketChannel;

public abstract class AbstractCommand implements Runnable {
  protected EventLoopResults eventloop;
  protected SocketChannel client;

  public SocketChannel getClient() { return client; }
  public EventLoopResults getEventloop() { return eventloop; }
  public void setEventloop(EventLoopResults eventloop) {
    this.eventloop = eventloop;
  }
  public void setClient(SocketChannel client) { this.client = client; }

  public abstract String toCLRF();

  public abstract void run();
}
