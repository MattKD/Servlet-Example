package kame;

import java.util.HashMap;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//
// Must be set as servlet-class in webapp/WEB-INF/web.xml.
// This uses very basic routing that isn't efficient, or advanced.
//
public class Servlet extends HttpServlet {
  private String prefix = "/pages";
  private String prefixFolder = "/pages/";
  private HashMap<String, Route> getRoutes = new HashMap<>();
  private HashMap<String, Route> postRoutes = new HashMap<>();
  private HashMap<String, Route> putRoutes = new HashMap<>();
  private HashMap<String, Route> deleteRoutes = new HashMap<>();

  private void setRoutes(Route[] routes) {
    for (Route r : routes) {
      for (String method : r.getMethods()) {
        switch (method.toUpperCase()) {
          case "GET":
            getRoutes.put(r.getPath(), r);
            break;
          case "POST":
            postRoutes.put(r.getPath(), r);
            break;
          case "PUT":
            putRoutes.put(r.getPath(), r);
            break;
          case "DELETE":
            deleteRoutes.put(r.getPath(), r);
            break;
        }
      }
    }
  }

  @Override
  public void init(ServletConfig config) {
    System.out.println("KameMVC Servlet init");

    String configClass = config.getInitParameter("config");
    if (configClass == null || configClass.equals("")) {
      System.out.println("Error: no Config given to servlet!");
      System.exit(1);
    }

    try {
      Class<?> clazz = Class.forName(configClass);
      Config userConfig = (Config) clazz.newInstance();
      userConfig.init();
      setRoutes(userConfig.getRoutes());
    } catch (Exception e) {
      System.out.println("Error: Invalid Config class '" + configClass + "'");
      System.exit(1);
    }
  }

  private void doStatic(HttpServletRequest req, HttpServletResponse res,
                        String path)
                throws ServletException, IOException {
    if (path.startsWith(prefixFolder)) {
      res.sendError(404);
    } else {
      RequestDispatcher rd = 
        req.getRequestDispatcher(path);
      rd.forward(req, res);
    }
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
    String path = req.getRequestURI().substring(req.getContextPath().length());
    path = path.substring(prefix.length());
  
    Route route = getRoutes.get(path);
    if (route != null) {
      route.getController().doRequest(req, res);
      return;
    }
    doStatic(req, res, path);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
    String path = req.getRequestURI().substring(req.getContextPath().length());
    path = path.substring(prefix.length());

    Route route = postRoutes.get(path);
    if (route != null) {
      route.getController().doRequest(req, res);
      return;
    }
 
    doStatic(req, res, path);
  }

  @Override
  public void doPut(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
    String path = req.getRequestURI().substring(req.getContextPath().length());
    path = path.substring(prefix.length());

    Route route = putRoutes.get(path);
    if (route != null) {
      route.getController().doRequest(req, res);
      return;
    }
 
    doStatic(req, res, path);
  }

  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
    String path = req.getRequestURI().substring(req.getContextPath().length());
    path = path.substring(prefix.length());

    Route route = deleteRoutes.get(path);
    if (route != null) {
      route.getController().doRequest(req, res);
      return;
    }

    doStatic(req, res, path);
  }
}
