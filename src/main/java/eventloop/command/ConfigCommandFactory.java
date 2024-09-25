package eventloop.command;

public class ConfigCommandFactory implements CommandFactory {

  @Override
  public ConfigCommand createCommand() {
    return new ConfigCommand();
  }
}
