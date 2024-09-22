package eventloop.command;

import eventloop.event.Storageable;
import eventloop.event.StorageableHolder;

public abstract class StorageCommand
    extends AbstractCommand implements StorageableHolder {
  protected String key;
  protected String value;
  protected Storageable storageable;
  protected boolean wasSuccesfull;

  public void setStorageable(Storageable storageable) {
    this.storageable = storageable;
  }
  public Storageable getStorageable() { return storageable; }

  public void setkey(String key) { this.key = key; }

  public String getkey() { return key; }

  public void setValue(String value) { this.value = value; }

  public String getValue() { return value; }
}
