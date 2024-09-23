package parser;

import eventloop.command.AbstractCommand;
import eventloop.command.Echo;
import eventloop.command.EchoFactory;
import eventloop.command.ErrorCommand;
import eventloop.command.ErrorCommandFactory;
import eventloop.command.Get;
import eventloop.command.GetFactory;
import eventloop.command.PingFactory;
import eventloop.command.SetFactory;
import eventloop.command.StorageCommand;
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

  public static AbstractCommand getErrorCommand(String errorCode) {
    ErrorCommand error = new ErrorCommandFactory().createCommand();
    error.setErrorMessage(errorCode);
    return error;
  }

  public static AbstractCommand Parse(String request) throws Exception {
    if (request == null) {
      return getErrorCommand("no command provided");
    }

    ArrayList<String> data = getData(request.toString());

    if (data.isEmpty()) {
      return getErrorCommand("invalid formar");
    }

    switch (data.get(0)) {
    case "PING":
      return new PingFactory().createCommand();

    case "ECHO":
      Echo echo = new EchoFactory().createCommand();
      echo.setArgument(data.get(1));
      return echo;

    case "SET":
      if (data.size() < 3) {
        return getErrorCommand(String.format(
            "SET command must contain a $key and $value arguments",
            data.get(0)));
      }
      SetFactory setfactory = new SetFactory();
      if (data.size() == 5) {
        try {
          int timeout = Integer.parseInt(data.get(4));
          setfactory.setTimeout(timeout);
        } catch (NumberFormatException e) {
          return getErrorCommand(String.format(
              "timeout must be an integer for command: '%s'", data.get(0)));
        }
      }
      StorageCommand set = setfactory.createCommand();
      set.setkey(data.get(1));
      set.setValue(data.get(2));
      return set;

    case "GET":
      Get get = new GetFactory().createCommand();
      get.setkey(data.get(1));
      return get;

    default:
      return getErrorCommand(
          (String.format("unkown command '%s'", data.get(0))));
    }
  }
}
