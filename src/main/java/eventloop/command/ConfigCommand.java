package eventloop.command;

import eventloop.event.EventLoopResults;
import eventloop.event.FinishedEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigCommand extends AbstractCommand {
  private String subcommand;
  private ArrayList<String> values;
  private HashMap<String, String> resultsMap;

  public ConfigCommand() {
    this.subcommand = "";
    this.values = new ArrayList<>();
    this.resultsMap = new HashMap<>();
  }

  public String getSubcommand() { return subcommand; }

  public void setSubcommand(String subcommand) { this.subcommand = subcommand; }

  public ArrayList<String> getValues() { return values; }

  public void setValues(ArrayList<String> values) { this.values = values; }

  public void executeGet() {
    EventLoopResults eventloop = this.getEventloop();
    for (String value : this.values) {
      String configVal = eventloop.getConfig().getFieldValue(value);
      if (configVal == null || configVal.isEmpty()) {
        continue;
      }
      this.resultsMap.put(value, configVal);
    }
  }

  @Override
  public String toCLRF() {
    StringBuilder result =
        new StringBuilder(String.format("*%d\r\n", this.values.size() * 2));
    this.resultsMap.forEach((key, value) -> {
      result.append(String.format("$%d\r\n%s\r\n$%d\r\n%s\r\n", key.length(),
                                  key, value.length(), value));
    });
    return result.toString();
  }

  @Override
  public void run() {
    this.executeGet();
    String result = this.toCLRF();
    FinishedEvent resultEvent = new FinishedEvent(result, this.client);
    this.eventloop.pushResultEvent(resultEvent);
  }
}
