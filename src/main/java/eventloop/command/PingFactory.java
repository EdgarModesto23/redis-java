package eventloop.command;

import eventloop.event.ResultEvent;

public class PingFactory implements CommandFactory {

  @Override
  public Command createCommand(ResultEvent eventloop) {
    return new Ping(eventloop);
  }
}
