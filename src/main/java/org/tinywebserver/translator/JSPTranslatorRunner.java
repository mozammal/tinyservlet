package org.tinywebserver.translator;

import org.apache.log4j.Logger;
import org.tinywebserver.config.TinyServletConfig;
import org.tinywebserver.controllers.TinyWebServer;
import org.tinywebserver.scanner.Scanner;
import org.tinywebserver.scanner.Source;
import org.tinywebserver.scanner.Token;
import org.tinywebserver.translator.JspToServletConverter;
import org.tinywebserver.translator.JspToServletProcessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public class JSPTranslatorRunner {

    private final static Logger LOG = Logger.getLogger(JSPTranslatorRunner.class);

    static {
        try {
            TinyServletConfig.getTinyServletConfigInstance().initializeServerConfigProperties();
            JspToServletProcessor.getJspToServletProcessorInstance().translateJspToServlet(false);
        } catch (IOException e) {
            LOG.info("Exception at jsp translation startup time");
        } catch (InterruptedException e) {
            LOG.info("Interrupted excetion at jsp translation startup time");
        } catch (ClassNotFoundException e) {
            LOG.info("class not found excetion at jsp translation startup time");
        } catch (URISyntaxException e) {
            LOG.info("class not found excetion at jsp translation startup time");
        }
    }

    public static void main(String... args) throws IOException, InterruptedException {

    }
}
