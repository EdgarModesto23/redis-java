package eventloop.command;

public class SetFactory implements CommandFactory {
  private int timeout;

  public SetFactory() { this.timeout = 0; }

  public void setTimeout(int timeout) { this.timeout = timeout; }

  public int getTimeout() { return timeout; }

  @Override
  public StorageCommand createCommand() {
    if (timeout > 0) {
      return new SetWithTimeout(timeout);
    }
    return new Set();
  }
}
