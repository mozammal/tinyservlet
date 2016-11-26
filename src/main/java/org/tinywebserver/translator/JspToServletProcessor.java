package org.tinywebserver.translator;

import org.tinywebserver.config.TinyServletConfig;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;


public class JspToServletProcessor {

    private static JspToServletProcessor jspToServletProcessor = new JspToServletProcessor();
    private JspToServletProcessor() {
    }

    public static JspToServletProcessor getJspToServletProcessorInstance() {
        return jspToServletProcessor;
    }

    public void translateJspToServlet(boolean updateServletConfiguration) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {

        TinyServletConfig tinyServletConfigInstance = TinyServletConfig.getTinyServletConfigInstance();
        JspToServletConverter jspToServletConverter = new JspToServletConverter(updateServletConfiguration);
        String jspDirectory = tinyServletConfigInstance.getProperties().getProperty("tinywebserver.jsp.directory");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL jspResourceDirectory = classloader.getResource(jspDirectory);

        if (jspResourceDirectory == null)
            return;

        File fileReader = new File(jspResourceDirectory.toURI());
        jspToServletConverter.traverseJspFolderAndConvertToJsp(fileReader);
    }


}
