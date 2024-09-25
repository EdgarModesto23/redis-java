package eventloop.event;

import eventloop.event.config.Configurable;

public interface EventLoopResults extends Configurable {
  public void pushResultEvent(FinishedEvent event);
}
