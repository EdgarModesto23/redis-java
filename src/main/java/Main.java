import eventloop.event.EventLoop;
import eventloop.event.config.Config;
import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Main {
  public static void main(String[] args) {

    System.out.println("Logs from your program will appear here!");

    try {
      Selector selector = Selector.open();
      ServerSocketChannel serverSocket = ServerSocketChannel.open();
      Config config = Config.getConfig(args);
      EventLoop eventloop = new EventLoop(selector, serverSocket, config);
      eventloop.StartServer();
    } catch (IOException e) {
      System.out.println(e);
      return;
    } catch (Exception e) {
      System.out.println(e);
      return;
    }
  }
}
