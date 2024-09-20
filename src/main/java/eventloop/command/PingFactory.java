package eventloop.command;

public class PingFactory implements CommandFactory {

  @Override
  public AbstractCommand createCommand() {
    return new Ping();
  }
}
