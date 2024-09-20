import eventloop.event.EventLoop;
import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Main {
  public static void main(String[] args) {

    System.out.println("Logs from your program will appear here!");

    try {
      Selector selector = Selector.open();
      ServerSocketChannel serverSocket = ServerSocketChannel.open();
      EventLoop eventloop = new EventLoop(selector, serverSocket);
      eventloop.StartServer();
    } catch (IOException e) {
      System.out.println(e);
      return;
    }
  }
}
