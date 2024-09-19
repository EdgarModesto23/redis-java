package eventloop.command;

import eventloop.event.ResultEvent;

public interface CommandFactory {
  Command createCommand(ResultEvent eventloop);
}
