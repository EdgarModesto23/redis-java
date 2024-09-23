package eventloop.command;

import eventloop.event.StorageableHolder;
import eventloop.event.StorageableWithTimeout;

public abstract class StorageCommandWithTimeout
    extends AbstractCommand implements StorageableHolder {
  protected String key;
  protected String value;
  protected StorageableWithTimeout storageable;
  protected boolean wasSuccesfull;

  public void setStorageable(StorageableWithTimeout storageable) {
    this.storageable = storageable;
  }
  public StorageableWithTimeout getStorageable() { return this.storageable; }

  public void setkey(String key) { this.key = key; }

  public String getkey() { return key; }

  public void setValue(String value) { this.value = value; }

  public String getValue() { return value; }
}
