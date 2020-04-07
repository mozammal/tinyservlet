package org.tinywebserver.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public abstract class BaseHttpProcessor {
  protected Socket socket;

  public BaseHttpProcessor(Socket socket) {
    this.socket = socket;
  }
}
