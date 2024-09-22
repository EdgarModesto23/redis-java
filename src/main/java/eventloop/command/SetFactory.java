package eventloop.command;

public class SetFactory implements CommandFactory {
  @Override
  public Set createCommand() {
    return new Set();
  }
}
