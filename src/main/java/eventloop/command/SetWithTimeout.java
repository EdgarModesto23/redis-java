package eventloop.command;

import eventloop.event.FinishedEvent;
import eventloop.event.Storageable;
import eventloop.event.StorageableWithTimeout;

public class SetWithTimeout extends StorageCommand {
  private int timeout;
  private StorageableWithTimeout storageable;

  public SetWithTimeout(int timeout) {
    this.key = "";
    this.value = "";
    this.wasSuccesfull = true;
    this.timeout = timeout;
  }

  public SetWithTimeout(String key, String value) {
    this.key = key;
    this.value = value;
    this.wasSuccesfull = true;
  }

  @Override
  public void setStorageable(Storageable storageable) {
    this.storageable = (StorageableWithTimeout)storageable;
  }

  @Override
  public StorageableWithTimeout getStorageable() {
    return storageable;
  }

  public void setTimeout(int timeout) { this.timeout = timeout; }

  public int getTimeout() { return timeout; }

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
    if (timeout > 0) {
      this.getStorageable().deleteKeyWithTimeout(this.key, this.timeout);
    }
  }
}
