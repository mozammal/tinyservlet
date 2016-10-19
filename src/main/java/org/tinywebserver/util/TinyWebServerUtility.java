package org.tinywebserver.util;

/**
 * Created by user on 10/19/2016.
 */
public class TinyWebServerUtility {

    public static String guessContentType(String fileName) {
        if (fileName.endsWith(".htm") || fileName.endsWith(".html"))
            return "text/html";
        else if (fileName.endsWith(".png"))
            return "image/png";
        else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
            return "image/jpeg";
        else if (fileName.endsWith(".wav"))
            return "audio/wav";
        else if (fileName.endsWith(".mpg") || fileName.endsWith(".mpeg"))
            return "video/mpeg";
        else if (fileName.endsWith(".txt") || fileName.endsWith(".text"))
            return "text/plain";
        else if (fileName.endsWith(".xml"))
            return "text/xml";
        else if (fileName.endsWith(".css"))
            return "text/css";
        else if (fileName.endsWith(".gif"))
            return "image/gif";

        return null;
    }
}
