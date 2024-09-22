
package eventloop.command;

public class GetFactory implements CommandFactory {

  @Override
  public Get createCommand() {
    return new Get();
  }
}
