package eventloop.event.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import util.Utils;

public class ConfigTests {

  @Test
  public void testContains() {
    String validInput = "port";
    assertTrue(Config.contains(validInput));

    String invalidInput = Utils.getSaltString();

    assertTrue(!Config.contains(invalidInput));
  }
}
