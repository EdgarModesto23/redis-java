package eventloop.event;

import eventloop.command.AbstractCommand;

public class StorageEventFactory implements EventAbstractFactory {
  private Storageable storage;

  public StorageEventFactory(Storageable storage) { this.storage = storage; }

  public StorageEvent createEvent(AbstractCommand command) {
    return new StorageEvent(storage);
  }
}
