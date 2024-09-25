package eventloop.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import eventloop.event.EventLoopResults;
import eventloop.event.config.Config;
import eventloop.event.util.TestEventLoopResults;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

// public class ConfigCommandTests {
//   //
//   // @Test
//   // public void testToCLRFGET() throws Exception {
//   //   String expectedString =
//   "*2\r\n$3\r\ndir\r\n$16\r\n/tmp/redis-files\r\n";
//   //
//   //   TestEventLoopResults eventloop = new TestEventLoopResults();
//   //   Config config =
//   //       Config.getConfig(new String[] {"--dir", "/tmp/redis-files"});
//   //   eventloop.setConfig(config);
//   //   ConfigCommand ConfigCommand = new ConfigCommand();
//   //   ConfigCommand.setEventloop(eventloop);
//   //   ConfigCommand.setSubcommand("GET");
//   //   ConfigCommand.setValues(
//   //       new ArrayList<>(Arrays.asList("dir", "/tmp/redis-files")));
//   //   ConfigCommand.executeGet();
//   //   assertEquals(expectedString, ConfigCommand.toCLRF());
//   // }
//   //
//   // @Test
//   // public void runTestGET() throws Exception {
//   //   String expectedString =
//   "*2\r\n$3\r\ndir\r\n$16\r\n/tmp/redis-files\r\n";
//   //   SocketChannel client = Mockito.mock(SocketChannel.class);
//   //   Config config =
//   //       Config.getConfig(new String[] {"--dir", "/tmp/redis-files"});
//   //   TestEventLoopResults eventloop = new TestEventLoopResults();
//   //   eventloop.setConfig(config);
//   //   ConfigCommand configCommand = new ConfigCommand();
//   //   configCommand.setClient(client);
//   //   configCommand.setSubcommand("GET");
//   //   configCommand.setValues(
//   //       new ArrayList<>(Arrays.asList("dir", "/tmp/redis-files")));
//   //   configCommand.setEventloop(eventloop);
//   //   configCommand.run();
//   //
//   //   assertEquals(expectedString,
//   //   eventloop.resultsQueue.remove().getResult());
//   // }
// }
