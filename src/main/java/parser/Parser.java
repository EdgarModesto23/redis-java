package parser;

import eventloop.command.AbstractCommand;
import eventloop.command.Echo;
import eventloop.command.EchoFactory;
import eventloop.command.Get;
import eventloop.command.GetFactory;
import eventloop.command.Ping;
import eventloop.command.PingFactory;
import eventloop.command.Set;
import eventloop.command.SetFactory;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

  public static ArrayList<String> getData(String request) {
    ArrayList<String> data = new ArrayList<>();
    if (request == null || request.isEmpty() || request.charAt(0) != '*') {
      return data;
    }
    String[] splitRequest = request.split("\r\n");
    if (splitRequest.length < 2) {
      return data;
    }
    splitRequest = Arrays.copyOfRange(splitRequest, 2, splitRequest.length);
    for (int i = 0; i < splitRequest.length; i++) {
      if (i % 2 == 0) {
        data.add(splitRequest[i]);
      }
    }

    return data;
  }

  public static AbstractCommand Parse(String request) throws Exception {
    ArrayList<String> data = getData(request.toString());
    System.out.println(data);

    switch (data.get(0)) {
    case "PING":
      return new PingFactory().createCommand();

    case "ECHO":
      Echo echo = new EchoFactory().createCommand();
      echo.setArgument(data.get(1));
      return echo;

    case "SET":
      Set set = new SetFactory().createCommand();
      set.setkey(data.get(1));
      set.setValue(data.get(2));
      return set;

    case "GET":
      Get get = new GetFactory().createCommand();
      get.setkey(data.get(1));
      return get;

    default:
      break;
    }

    return new Ping();
  }
}
