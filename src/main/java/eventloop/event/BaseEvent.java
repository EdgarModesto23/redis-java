package eventloop.event;

import eventloop.command.AbstractCommand;

public class BaseEvent extends AbstractEvent {
  public BaseEvent(AbstractCommand command) { this.command = command; }

  @Override
  public void RunAbstractCommand() {
    new Thread(this.command).start();
  }
}
