
package eventloop.command;

public class EchoFactory implements CommandFactory {

  @Override
  public Echo createCommand() {
    return new Echo();
  }
}
