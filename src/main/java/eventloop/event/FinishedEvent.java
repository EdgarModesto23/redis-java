package eventloop.event;

import java.nio.channels.SocketChannel;

/**
 * FinishedEvent
 */
public class FinishedEvent {
  private String result;
  private SocketChannel client;

  public FinishedEvent(String result, SocketChannel client) {
    this.client = client;
    this.result = result;
  }

  public SocketChannel getClient() { return client; }

  public String getResult() { return result; }

  public void setClient(SocketChannel client) { this.client = client; }

  public void setResult(String result) { this.result = result; }
}
