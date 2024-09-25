package eventloop.event.util;

import eventloop.event.EventLoopResults;
import eventloop.event.FinishedEvent;
import eventloop.event.config.Config;
import java.util.LinkedList;
import java.util.Queue;

public final class TestEventLoopResults implements EventLoopResults {
  private Config config;
  public Queue<FinishedEvent> resultsQueue = new LinkedList<>();

  public void setConfig(Config config) { this.config = config; }

  public Config getConfig() { return this.config; }
  public void pushResultEvent(FinishedEvent event) {
    this.resultsQueue.add(event);
  }
}
