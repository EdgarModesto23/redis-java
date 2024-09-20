package eventloop.command;

import eventloop.event.FinishedEvent;

public class Echo extends AbstractCommand {
  private String argument;

  public Echo() { this.argument = ""; }

  public Echo(String arg) { this.argument = arg; }

  public void setArgument(String argument) { this.argument = argument; }

  public String getArgument() { return argument; }

  @Override
  public String toCLRF() {
    return String.format("$%d\r\n%s\r\n", this.argument.length(),
                         this.argument);
  }
  @Override
  public void run() {
    String result = this.toCLRF();
    FinishedEvent resultEvent = new FinishedEvent(result, this.client);
    this.eventloop.pushResultEvent(resultEvent);
  }
}
