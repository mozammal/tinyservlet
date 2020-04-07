package org.tinywebserver.config;

import org.tinywebserver.servlet.example.LoginSuccessTinyServletDemo;
import org.tinywebserver.servlet.example.LoginTinyServletDemo;
import org.tinywebserver.servlet.TinyServlet;
import org.tinywebserver.translator.JspToServletConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TinyServletConfig {
  public static final String LOGIN_SERVLET_NAME = "/login";
  public static final String HOME_SERVLET_NAME = "/home";
  public static final String APPLICATION_PROPERTIES = "application.properties";
  private Class cls;
  private ConcurrentMap<String, Class> tinyServletConfigs = new ConcurrentHashMap<String, Class>();
  private Properties properties = new Properties();

  private static TinyServletConfig tinyServletConfig;

  private TinyServletConfig() {}

  public static synchronized TinyServletConfig getTinyServletConfigInstance() throws IOException {
    if (tinyServletConfig == null) {
      tinyServletConfig = new TinyServletConfig();
    }
    return tinyServletConfig;
  }

  public void initializeServerConfigProperties() throws IOException {
    tinyServletConfigs = new ConcurrentHashMap<String, Class>();
    tinyServletConfigs.put(LOGIN_SERVLET_NAME, LoginTinyServletDemo.class);
    tinyServletConfigs.put(HOME_SERVLET_NAME, LoginSuccessTinyServletDemo.class);
    properties = new Properties();
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    properties.load(classloader.getResourceAsStream(APPLICATION_PROPERTIES));
  }

  public TinyServlet getMatchingTinyServlet(String resource)
      throws IllegalAccessException, InstantiationException, IOException {

    Class clss = getTinyServletConfigs().get(resource);
    if (clss != null) {
      Object instance = clss.newInstance();
      TinyServlet tinyServlet = (TinyServlet) instance;
      return tinyServlet;
    }
    return null;
  }

  public ConcurrentMap<String, Class> getTinyServletConfigs() {
    return tinyServletConfigs;
  }

  public Properties getProperties() {
    return properties;
  }

  public String getProperty(String prop) {
    return properties.getProperty(prop);
  }
}
