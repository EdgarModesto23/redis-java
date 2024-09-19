package eventloop.command;

import eventloop.event.ResultEvent;

public class Ping implements Command {
  private ResultEvent eventloop;

  Ping(ResultEvent eventloop) { this.eventloop = eventloop; }

  @Override
  public void run() {
    eventloop.EnqueueResult(this);
  };
}
