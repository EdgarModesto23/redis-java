package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import eventloop.command.Echo;
import eventloop.command.ErrorCommand;
import eventloop.command.Ping;
import eventloop.command.Set;
import eventloop.command.SetWithTimeout;
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
  public void testGetCorrectClass() {
    try {
      assertEquals(Ping.class, Parser.Parse("*1\r\n$4\r\nPING\r\n").getClass());
      assertEquals(
          Echo.class,
          Parser.Parse("*2\r\n$4\r\nECHO\r\n$3\r\nhey\r\n").getClass());
      assertTrue(Parser.Parse("*1\r\n$7\r\nINVALID") instanceof ErrorCommand);
      assertTrue(Parser.Parse("*1\r\n$0\r\n") instanceof ErrorCommand);
      assertTrue(Parser.Parse("") instanceof ErrorCommand);
      assertTrue(Parser.Parse(null) instanceof ErrorCommand);
    } catch (Exception e) {
      fail(e.toString());
    }
  }

  @Test
  public void getSetWithoutTimeout() {
    try {
      assertEquals(Set.class,
                   Parser.Parse("*5\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\nbar\r\n")
                       .getClass());

    } catch (Exception e) {
      fail(e.toString());
    }
  }

  @Test
  public void getSetWithTimeout() {
    try {
      assertEquals(SetWithTimeout.class,
                   Parser
                       .Parse("*5\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\nbar\r\n$"
                              + "2\r\npx\r\n$3\r\n100\r\n")
                       .getClass());

    } catch (Exception e) {
      fail(e.toString());
    }
  }
}
