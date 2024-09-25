package eventloop.event.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;

public class Config {
  private int port;
  private String dir;
  private String dbfilename;
  private HashMap<String, String> fieldsMap;

  public Config() {
    this.port = 6379;
    this.dir = null;
    this.dbfilename = null;
    this.fieldsMap = new HashMap<>();
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

  public int getPort() { return port; }

  public void setPort(int port) { this.port = port; }

  public String getDbfilename() { return dbfilename; }

  public String getDir() { return dir; }

  public void setDir(String dir) { this.dir = dir; }

  public void setDbfilename(String dbfilename) { this.dbfilename = dbfilename; }

  public static boolean contains(String fieldname) {
    Field[] fields = Config.class.getDeclaredFields();
    for (Field field : fields) {
      if (field.getName().equals(fieldname)) {
        return true;
      }
    }
    return false;
  }

  public static Config getConfig(String[] args) throws Exception {
    Config config = new Config();
    if (args == null || args.length == 0) {
      return config;
    }
    if (args.length % 2 != 0) {
      throw new Exception("-ERR invalid number of arguments\r\n");
    }
    Class<Config> configClass = Config.class;
    for (int i = 0; i < args.length; i += 2) {
      String flag = args[i].substring(2);
      if (!contains(flag)) {
        throw new Exception(String.format("-ERR invalid flag: ", flag));
      }
      Field field = configClass.getDeclaredField(flag);
      field.setAccessible(true);
      Class<?> type = field.getType();
      if (type == int.class) {
        try {
          field.set(config, Integer.parseInt(args[i + 1]));
          config.fieldsMap.put(field.getName(), args[i + 1]);
        } catch (NumberFormatException e) {
          throw new Exception(String.format("-ERR invalid value %s for: %s ",
                                            args[i + 1], flag));
        }
      } else if (type == String.class) {
        field.set(config, args[i + 1]);
        config.fieldsMap.put(field.getName(), args[i + 1]);
      } else {
        throw new Exception("-ERR invalid type on: " + flag);
      }
    }
    return config;
  }
}
