package eventloop.command;

public class PingFactory implements CommandFactory {

  @Override
  public Command createCommand() {
    return new Ping();
  }
}
