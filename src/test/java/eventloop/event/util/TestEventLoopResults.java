package eventloop.event.util;

import eventloop.event.EventLoopResults;
import eventloop.event.FinishedEvent;
import java.util.LinkedList;
import java.util.Queue;

public final class TestEventLoopResults implements EventLoopResults {
  public Queue<FinishedEvent> resultsQueue = new LinkedList<>();

  public void pushResultEvent(FinishedEvent event) {
    this.resultsQueue.add(event);
  }
}
