package eventloop.command;

public class ErrorCommandFactory implements CommandFactory {

  @Override
  public ErrorCommand createCommand() {
    return new ErrorCommand();
  }
}
