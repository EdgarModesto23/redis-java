package eventloop.event;

import eventloop.command.AbstractCommand;

public class BaseEventFactory implements EventAbstractFactory {
  public AbstractEvent createEvent(AbstractCommand command) {
    return new BaseEvent(command);
  }
}
