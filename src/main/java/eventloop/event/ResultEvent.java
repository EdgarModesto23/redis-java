package eventloop.event;

import eventloop.command.Command;

public interface ResultEvent {
  void EnqueueResult(Command result);
}
