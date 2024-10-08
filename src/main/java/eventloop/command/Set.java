package eventloop.command;

import eventloop.event.FinishedEvent;
import eventloop.event.Storageable;

public class Set extends StorageCommand {
  private Storageable storageable;

  public Set() {
    this.key = "";
    this.value = "";
    this.wasSuccesfull = true;
  }

  public Set(String key, String value) {
    this.key = key;
    this.value = value;
    this.wasSuccesfull = true;
  }

  @Override
  public void setStorageable(Storageable storageable) {
    this.storageable = storageable;
  }

  @Override
  public Storageable getStorageable() {
    return storageable;
  }

  @Override
  public String toCLRF() {
    if (this.wasSuccesfull) {
      return "+OK\r\n";
    }
    return "";
  }

  @Override
  public void run() {
    this.storageable.addValueToStorage(this.key, this.value);
    String result = this.toCLRF();
    FinishedEvent resultEvent = new FinishedEvent(result, this.client);
    this.eventloop.pushResultEvent(resultEvent);
  }
}
