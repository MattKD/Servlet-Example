package kame;

import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.apache.catalina.Wrapper;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class App {
  private int port = 8080;
  private String debugWebappDir = "src/main/webapp/";
  private String prodWebappDir = "target/webapp/";
  private boolean debug = true;

  public App setPort(int port) {
    this.port = port;
    return this;
  }

  public int getPort() {
    return port;
  }

  public App setDebug(boolean debug) {
    this.debug = debug;
    return this;
  }

  public boolean getDebug() {
    return debug;
  }

  public App setDebugWebappDir(String dir) {
    debugWebappDir = dir;
    return this;
  }

  public App setProdWebappDir(String dir) {
    prodWebappDir = dir;
    return this;
  }

  public String getWebappDir() {
    if (debug) {
      return debugWebappDir;
    } else {
      return prodWebappDir;
    }
  }

  //
  // Initializes embedded tomcat. Uses "webapp/WEB-INF/web.xml" and
  // "webapp/META-INF/context.xml". This doesn't configure a server.xml,
  // global web.xml, or https/ssl.
  //
  public void run() throws Exception {
    String debugEnvVar = "KAME_DEBUG";
    String debugEnvStr = System.getenv(debugEnvVar);
    if (debugEnvStr == null) {
      debugEnvStr = "";
    }
    if (debugEnvStr.equals("FALSE")) {
      setDebug(false);
    } else if (debugEnvStr.equals("TRUE")) {
      setDebug(true);
    }

    System.out.println("Kame_MVC initializing");
    System.out.println(debugEnvVar + " = " + debugEnvStr);
    if (getDebug() == true) {
      System.out.println("Running in debug mode. To change to production, " +
          "set the environmental variable '" + debugEnvVar + "' to FALSE");
    } else {
      System.out.println("Running in production mode. To change to debug, " +
          "set the environmental variable '" + debugEnvVar + "' to TRUE");
    }

    File webappFile = new File(getWebappDir());
    if (webappFile.exists() == false) {
      System.out.println("Error: webapp directory: " + 
          webappFile.getAbsolutePath() + " does not exist!");
      System.exit(1);
    } else {
      System.out.println("Using webapp directory: " + 
          webappFile.getAbsolutePath());
    }

    File configFile = new File(getWebappDir() + "META-INF/context.xml");
    if (configFile.exists() == false) {
      System.out.println("Error: " + configFile.getAbsolutePath() + 
                         " does not exist!");
      System.exit(1);
    } else {
      System.out.println("Using: " + configFile.getAbsolutePath());
    }

    Tomcat tomcat = new Tomcat();
    tomcat.enableNaming();

    tomcat.setPort(getPort());

    StandardContext ctx = 
      (StandardContext) tomcat.addWebapp("", webappFile.getAbsolutePath());
    ctx.setConfigFile(configFile.toURI().toURL());

    System.out.println("tomcat start\n");
    tomcat.init();
    tomcat.start();
    System.out.println("Running at: http://localhost:" + port + "/");
    tomcat.getServer().await();
  }
}
