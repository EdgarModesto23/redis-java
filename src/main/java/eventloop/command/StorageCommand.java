package eventloop.command;

import eventloop.event.Storageable;
import eventloop.event.StorageableHolder;

public abstract class StorageCommand
    extends AbstractCommand implements StorageableHolder {
  protected String key;
  protected String value;
  protected boolean wasSuccesfull;

  public abstract void setStorageable(Storageable storageable);
  public abstract Storageable getStorageable();

  public void setkey(String key) { this.key = key; }

  public String getkey() { return key; }

  public void setValue(String value) { this.value = value; }

  public String getValue() { return value; }
}
