package eventloop.event;

public interface Storageable {
  public void addValueToStorage(String key, String value);
  public String getValueFromStorage(String key);
}
