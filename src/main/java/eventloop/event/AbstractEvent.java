package eventloop.event;

import eventloop.command.AbstractCommand;

public abstract class AbstractEvent {
  protected AbstractCommand command;

  public abstract void RunAbstractCommand();

  public void setAbstractCommand(AbstractCommand command) {
    this.command = command;
  }

  public AbstractCommand getAbstractCommand() { return command; }
}
