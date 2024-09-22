package eventloop.command;

import eventloop.event.FinishedEvent;

public class Set extends StorageCommand {

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
  public String toCLRF() {
    if (this.wasSuccesfull) {
      return "+OK\r\n";
    }
    return "";
  }

  @Override
  public void run() {
    this.storageable.addValueToStorage(this.key, this.value);
    System.out.println(this.storageable.getValueFromStorage(this.key));
    String result = this.toCLRF();
    FinishedEvent resultEvent = new FinishedEvent(result, this.client);
    this.eventloop.pushResultEvent(resultEvent);
  }
}
