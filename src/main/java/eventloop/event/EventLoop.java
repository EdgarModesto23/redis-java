package eventloop.event;

import eventloop.command.AbstractCommand;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import parser.Parser;

public class EventLoop implements EventLoopResults, StorageableWithTimeout {
  private Selector selector;
  private ServerSocketChannel serverSocket;
  private Queue<AbstractEvent> processQueue;
  private Queue<FinishedEvent> resultsQueue;
  private HashMap<String, String> storage;

  public EventLoop(Selector selector, ServerSocketChannel serverSocket) {
    this.selector = selector;
    this.serverSocket = serverSocket;
    this.processQueue = new LinkedList<>();
    this.resultsQueue = new LinkedList<>();
    this.storage = new HashMap<>();
  }

  public void deleteKeyFromStorageable(String key) { this.storage.remove(key); }

  public void deleteKeyWithTimeout(String key, int timeout) {
    Runnable deleteKey = () -> {
      try {
        Thread.sleep(timeout);
        this.deleteKeyFromStorageable(key);
      } catch (InterruptedException e) {
        System.out.println(e);
      }
    };
    Thread thread = new Thread(deleteKey);
    thread.start();
  }

  public String getValueFromStorage(String key) {
    return this.storage.get(key);
  }

  public void addValueToStorage(String key, String value) {
    this.storage.put(key, value);
  }

  public void pushResultEvent(FinishedEvent event) {
    this.resultsQueue.add(event);
  }

  public static void answerClient(FinishedEvent result) throws IOException {
    result.getClient().write(ByteBuffer.wrap(result.getResult().getBytes()));
  }

  public final void setup() throws IOException {
    int port = 6379;
    this.serverSocket.bind(new InetSocketAddress(port));
    this.serverSocket.configureBlocking(false);
    this.serverSocket.register(selector, SelectionKey.OP_ACCEPT);
  }

  public final void StartServer() {
    try {
      this.setup();
      while (true) {
        this.selector.select(1);
        // define a set of selectable keys
        Iterator<SelectionKey> keyIterator =
            this.selector.selectedKeys().iterator();

        if (!this.resultsQueue.isEmpty()) {
          try {
            FinishedEvent result = this.resultsQueue.remove();
            answerClient(result);
          } catch (IOException e) {
            System.err.println(e);
          }
        }
        if (!this.processQueue.isEmpty()) {
          AbstractEvent newEvent = this.processQueue.remove();
          newEvent.RunAbstractCommand();
        }
        // iterate over the selected keys
        while (keyIterator.hasNext()) {
          SelectionKey key = keyIterator.next();
          keyIterator.remove();

          /*if both the server and the client have binded to a port and
          both are ready to share data with one another isAcceptable()
          will return true */

          if (key.isAcceptable()) {
            SocketChannel client = this.serverSocket.accept();

            client.configureBlocking(false);

            client.register(this.selector, SelectionKey.OP_READ);
          }
          /* if there is any data while is yet to be read by the server
            isReadable returns true. Note that each operation of sendData() is
            mapped to a key and only if new data is present and unread
             isReadable() will return true */

          else if (key.isReadable()) {

            SocketChannel client = (SocketChannel)key.channel();

            ByteBuffer serverBuffer = ByteBuffer.allocate(1024);

            // do something with your data
            int bytesRead = client.read(serverBuffer);

            if (bytesRead == -1) {
              client.close();
            } else {
              serverBuffer.flip();
              byte[] byteArray = new byte[bytesRead];
              serverBuffer.get(byteArray);
              String result = new String(byteArray, StandardCharsets.UTF_8);
              try {
                AbstractCommand command = Parser.Parse(result);
                command.setClient(client);
                command.setEventloop(this);
                AbstractEvent newEvent =
                    new EventFactory().createEvent(command, this);
                this.processQueue.add(newEvent);
                serverBuffer.clear();
              } catch (Exception e) {
                client.write(ByteBuffer.wrap(e.toString().getBytes()));
                serverBuffer.clear();
              }
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
