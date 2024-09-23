package eventloop.event;

import eventloop.command.StorageCommand;

public class StorageEvent extends AbstractEvent {
  private Storageable storage;
  private StorageCommand command;

  public StorageEvent(Storageable storage) { this.storage = storage; }

  public void setCommand(StorageCommand command) { this.command = command; }

  public StorageCommand getCommand() { return command; }

  @Override
  public void RunAbstractCommand() {
    this.getCommand().setStorageable(storage);
    System.out.println(this.getCommand().getStorageable());
    new Thread(this.command).start();
  }
}
