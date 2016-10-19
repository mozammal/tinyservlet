package org.tinywebserver.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * Created by user on 10/16/2016.
 */
public abstract class BaseClientProcessor {
    protected Socket socket;

    public BaseClientProcessor(Socket socket) {
        this.socket = socket;
    }

    public abstract void parseRequest();

    public abstract void prepareResponse();


}
