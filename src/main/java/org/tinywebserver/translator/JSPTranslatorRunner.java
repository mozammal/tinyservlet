package org.tinywebserver.translator;

import org.apache.log4j.Logger;
import org.tinywebserver.config.TinyServletConfig;

import java.io.IOException;
import java.net.URISyntaxException;


public class JSPTranslatorRunner {

    private final static Logger LOG = Logger.getLogger(JSPTranslatorRunner.class);

    static {
        try {
            TinyServletConfig.getTinyServletConfigInstance().initializeServerConfigProperties();
            JspToServletProcessor.getJspToServletProcessorInstance().translateJspToServlet(false);
        } catch (IOException e) {
            LOG.info("Exception at jsp translation time");
        } catch (InterruptedException e) {
            LOG.info("Interrupted excetion at jsp translation time");
        } catch (ClassNotFoundException e) {
            LOG.info("class not found excetion at jsp translation time");
        } catch (URISyntaxException e) {
            LOG.info("class not found excetion at jsp translation time");
        }
    }

    public static void main(String... args) throws IOException, InterruptedException {

    }
}
