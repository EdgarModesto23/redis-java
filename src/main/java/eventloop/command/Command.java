package eventloop.command;

public interface Command extends Runnable {
  void run();
}
