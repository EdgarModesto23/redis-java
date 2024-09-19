import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible
    // when running tests.
    System.out.println("Logs from your program will appear here!");

    try {
      Selector selector = Selector.open();
      ServerSocketChannel serverSocket = ServerSocketChannel.open();
      int port = 6379;
      serverSocket.bind(new InetSocketAddress(port));
      serverSocket.configureBlocking(false);
      serverSocket.register(selector, SelectionKey.OP_ACCEPT);
      while (true) {
        selector.select();
        // define a set of selectable keys
        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

        // iterate over the selected keys
        while (keyIterator.hasNext()) {
          SelectionKey key = keyIterator.next();
          keyIterator.remove();

          /*if both the server and the client have binded to a port and
          both are ready to share data with one another isAcceptable()
          will return true */

          if (key.isAcceptable()) {
            SocketChannel client = serverSocket.accept();

            client.configureBlocking(false);

            client.register(selector, SelectionKey.OP_READ);
          }
          /* if there is any data while is yet to be read by the server
            isReadable returns true. Note that each operation of sendData() is
            mapped to a key and only if new data is present and unread
             isReadable() will return true */

          else if (key.isReadable()) {

            SocketChannel client = (SocketChannel)key.channel();

            ByteBuffer serverBuffer = ByteBuffer.allocate(1024);

            client.read(serverBuffer);

            InputStream in = new ByteArrayInputStream(serverBuffer.array());

            // do something with your data
            int bytesRead = client.read(serverBuffer);

            if (bytesRead == -1) {
              client.close();
            } else {
              serverBuffer.flip();
              client.write(ByteBuffer.wrap("+PONG\r\n".getBytes()));
              serverBuffer.clear();
            }
          }
        }
      }
    } catch (IOException e) {
      System.out.println(e);
      return;
    }
  }
}
