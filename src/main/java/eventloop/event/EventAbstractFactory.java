package eventloop.event;

import eventloop.command.AbstractCommand;

public interface EventAbstractFactory {
  public AbstractEvent createEvent(AbstractCommand command);
}
