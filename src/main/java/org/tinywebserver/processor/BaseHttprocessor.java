package org.tinywebserver.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * Created by user on 10/16/2016.
 */
public abstract class BaseHttprocessor {

    protected Socket socket;

    public BaseHttprocessor(Socket socket) {
        this.socket = socket;
    }
}
