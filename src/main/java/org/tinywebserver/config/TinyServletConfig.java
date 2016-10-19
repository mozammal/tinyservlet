package org.tinywebserver.config;

import org.tinywebserver.servlet.example.LoginSuccessTinyServletDemo;
import org.tinywebserver.servlet.example.LoginTinyServletDemo;
import org.tinywebserver.servlet.TinyServlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TinyServletConfig {
    public static final String LOGIN_SERVLET_NAME = "/login";
    public static final String HOME_SERVLET_NAME = "/home";
    public static final String APPLICATION_PROPERTIES = "application.properties";
    private Class cls;

    private static HashMap<String, TinyServletConfig> tinyServletConfigs;
    private static Properties properties;

    private TinyServletConfig(Class cls) {
        this.cls = cls;
    }

    public static synchronized Map<String, TinyServletConfig> getTinyServletConfigInstance() throws IOException {
        if (tinyServletConfigs == null) {
            tinyServletConfigs = new HashMap<String, TinyServletConfig>();
            tinyServletConfigs.put(LOGIN_SERVLET_NAME, new TinyServletConfig(LoginTinyServletDemo.class));
            tinyServletConfigs.put(HOME_SERVLET_NAME, new TinyServletConfig(LoginSuccessTinyServletDemo.class));
            properties = new Properties();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            properties.load(classloader.getResourceAsStream(APPLICATION_PROPERTIES));
        }
        return tinyServletConfigs;
    }

    public static TinyServlet getMatchingTinyServlet(String resource) throws IllegalAccessException, InstantiationException, IOException {

        TinyServletConfig config = getTinyServletConfigInstance().get(resource);
        if (config != null) {
            Class cls = config.getCls();
            Object instance = cls.newInstance();
            TinyServlet tinyServlet = (TinyServlet) instance;
            return tinyServlet;
        }
        return null;
    }

    public Class getCls() {
        return cls;
    }

    public static String getProperty(String prop) {
        return properties.getProperty(prop);
    }

}
