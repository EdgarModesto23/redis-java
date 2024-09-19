package eventloop.event;

import eventloop.command.Command;
import java.util.LinkedList;
import java.util.Queue;

public class EventLoop implements ResultEvent {
  private Queue<Command> EventQueue;
  private Queue<Command> ResultQueue;

  public EventLoop() {
    this.ResultQueue = new LinkedList<>();
    this.EventQueue = new LinkedList<>();
  }

  public void EnqueueEvent(Command command) { this.EventQueue.add(command); }

  public Command DequeueEvent() { return this.EventQueue.remove(); }

  public boolean isEventQueueEmpty() { return this.EventQueue.isEmpty(); }

  public Command DequeueResult() { return this.ResultQueue.remove(); }

  public void EnqueueResult(Command result) { this.ResultQueue.add(result); }

  public boolean isResultQueueEmpty() { return this.ResultQueue.isEmpty(); }
}
