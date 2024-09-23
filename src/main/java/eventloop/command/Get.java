package eventloop.command;

import eventloop.event.FinishedEvent;
import eventloop.event.Storageable;

public class Get extends StorageCommand {
  private Storageable storageable;

  public Get() {
    this.key = "";
    this.value = "";
    this.wasSuccesfull = true;
  }

  public Get(String key, String value) {
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
      return String.format("$%d\r\n%s\r\n", this.value.length(), this.value);
    }
    return "$-1\r\n";
  }

  @Override
  public void run() {
    String value = this.storageable.getValueFromStorage(this.key);
    if (value == null) {
      this.wasSuccesfull = false;
    } else {
      this.setValue(value);
    }
    String result = this.toCLRF();
    FinishedEvent resultEvent = new FinishedEvent(result, this.client);
    this.eventloop.pushResultEvent(resultEvent);
  }
}
