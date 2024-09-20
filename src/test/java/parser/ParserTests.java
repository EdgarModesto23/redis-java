package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import eventloop.command.Echo;
import eventloop.command.Ping;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class ParserTests {

  @Test
  public void testCorrectOutput() {
    ArrayList<String> output = new ArrayList<>();
    output.add("ECHO");
    output.add("hey");
    assertEquals(output, Parser.getData("*2\r\n$4\r\nECHO\r\n$3\r\nhey\r\n"));
  }

  @Test
  public void testMultipleValues() {
    ArrayList<String> output = new ArrayList<>();
    output.add("Command");
    output.add("hey");
    output.add("1234");
    output.add("123456");
    assertEquals(output, Parser.getData("*4\r\n$7\r\nCommand\r\n$3\r\nhey\r\n$"
                                        + "4\r\n1234\r\n$6\r\n123456\r\n"));
  }

  @Test
  public void testWrongFormat() {
    ArrayList<String> output = new ArrayList<>();
    assertEquals(output, Parser.getData("12345\r\n1234\r\n"));
    assertEquals(output, Parser.getData("*21234 123 123"));
    assertEquals(output, Parser.getData(null));
    assertEquals(output, Parser.getData(""));
    assertEquals(output, Parser.getData("*2\r\n$0\r\n"));
  }

  @Test
  public void testParseData() {
    ArrayList<String> output = new ArrayList<>();
    try {
      assertEquals(Ping.class, Parser.Parse("*1\r\n$4\r\nPING\r\n").getClass());
      assertEquals(
          Echo.class,
          Parser.Parse("*2\r\n$4\r\nECHO\r\n$3\r\nhey\r\n").getClass());
    } catch (Exception e) {
      assertEquals(false, true);
    }
  }
}
