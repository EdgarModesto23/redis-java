package util;

import eventloop.event.EventLoopResults;
import eventloop.event.FinishedEvent;
import eventloop.event.config.Config;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Utils {
  public static String getSaltString() {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 18) { // length of the random string.
      int index = (int)(rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    return saltStr;
  }
}
