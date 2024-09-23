package eventloop.event;

public interface StorageableWithTimeout extends Storageable {
  public void deleteKeyFromStorageable(String key);
  public void deleteKeyWithTimeout(String key, int timeout);
}
