package eventloop.event;

import eventloop.command.AbstractCommand;

public class Event {
  private AbstractCommand command;

  public Event(AbstractCommand command) { this.command = command; }

  public void RunAbstractCommand() { new Thread(this.command).start(); }

  public void setAbstractCommand(AbstractCommand command) {
    this.command = command;
  }

  public AbstractCommand getAbstractCommand() { return command; }
}
