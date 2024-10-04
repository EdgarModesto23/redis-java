package eventloop.event.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;

public class Config {
  private HashMap<String, String> fieldsMap;

  public Config() {
    this.fieldsMap = new HashMap<>() {
      {
        put("dir", "/var/opt/");
        put("dbfilename", "dump.rdb");
        put("port", "6379");
      }
    };
  }

  public void setFieldValue(String key, String value) {
    this.fieldsMap.put(key, value);
  }

  public String getFieldValue(String key) { return this.fieldsMap.get(key); }

  public boolean equals(Config obj) {
    Class<?> clazz = Config.class;
    Field[] fields = clazz.getDeclaredFields();

    for (Field field : fields) {
      field.setAccessible(true);
      try {
        Object value1 = field.get(this);
        Object value2 = field.get(obj);

        if (!Objects.equals(value1, value2)) {
          return false;
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public int getPort() { return Integer.parseInt(this.fieldsMap.get("port")); }

  public void setPort(int port) {
    this.fieldsMap.put("port", Integer.toString(port));
  }

  public static boolean canBeConvertedToInt(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static Config getConfig(String[] args) throws Exception {
    Config config = new Config();
    if (args == null || args.length == 0) {
      return config;
    }
    if (args.length % 2 != 0) {
      throw new Exception("-ERR invalid number of arguments\r\n");
    }
    for (int i = 0; i < args.length; i += 2) {
      String flag = args[i].substring(2);
      if (config.fieldsMap.get(flag) == null) {
        throw new Exception(String.format("Argument: %s not valid.", flag));
      }
      config.fieldsMap.put(flag, args[i + 1]);
    }
    return config;
  }
}
