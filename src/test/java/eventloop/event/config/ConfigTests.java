package eventloop.event.config;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ConfigTests {

  @Test
  public void testConfigProps() {
    Config config = new Config();

    assertTrue(config.getFieldValue("port") != null);
    ;
    assertTrue(config.getFieldValue("dir") != null);
    ;
    assertTrue(config.getFieldValue("dbfilename") != null);
    ;
  }

  @Test
  public void testGetConfigFromArgsCorrect() {
    String[] args =
        new String[] {"--dir", "/var/db/", "--dbfilename", "file.rdb"};

    try {
      Config conf = Config.getConfig(args);

      assertEquals("/var/db/", conf.getFieldValue("dir"));
      assertEquals("file.rdb", conf.getFieldValue("dbfilename"));
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void testGetConfigrFromArgsIncorrectFlag() {
    String[] args = new String[] {"--dir", "/var/db/", "--dbfilename",
                                  "file.rdb", "--incorrect"};

    assertThrows(Exception.class, () -> {
      Config.getConfig(args);
    }, "getConfig didn't throw error on incorrect number of flags");

    String[] argsIncorrectFlagFormat =
        new String[] {"-dir", "/var/db/", "-dbfilename", "file.rdb"};

    assertThrows(Exception.class, () -> {
      Config.getConfig(argsIncorrectFlagFormat);
    }, "getConfig didn't throw error on incorrect format for flags");

    String[] argsBadFlag =
        new String[] {"--bad", "/var/db/", "--flag", "file.rdb"};

    assertThrows(Exception.class, () -> {
      Config.getConfig(argsBadFlag);
    }, "getConfig didn't throw error on unkown flag");
  }
}
