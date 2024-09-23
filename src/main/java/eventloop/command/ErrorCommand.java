package eventloop.command;

import eventloop.event.FinishedEvent;

public class ErrorCommand extends AbstractCommand {
  protected String errorMessage;

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() { return errorMessage; }

  @Override
  public String toCLRF() {
    return String.format("-ERR %s\r\n", this.errorMessage);
  }

  public void run() {
    String result = this.toCLRF();
    FinishedEvent resultEvent = new FinishedEvent(result, this.client);
    this.eventloop.pushResultEvent(resultEvent);
  }
}
