package eventloop.event;

import eventloop.command.AbstractCommand;
import eventloop.command.StorageCommand;

public class EventFactory {
  public AbstractEvent createEvent(AbstractCommand command,
                                   Storageable storage) {
    if (command instanceof StorageCommand) {
      StorageCommand storageCommand = (StorageCommand)command;
      StorageEvent event =
          new StorageEventFactory(storage).createEvent(command);
      event.setCommand(storageCommand);
      return event;
    }
    return new BaseEvent(command);
  }
}
