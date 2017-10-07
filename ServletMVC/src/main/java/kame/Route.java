package kame;

//
// Class for basic routes
// Only supports paths: "/", "/home", "/foo/bar_baz"
// Doesn't support path params: "/user/<name>"
// Supported methods are: "GET", "POST", "PUT", "DELETE"
// "GET" is default
//
public class Route {
  private String path;
  private Controller controller;
  private String[] methods;

  public Route(String path, Controller controller) {
    this.path = path;
    this.controller = controller;
    this.methods = new String[] { "GET" };
  }

  public Route(String path, Controller controller, String method) {
    this.path = path;
    this.controller = controller;
    this.methods = new String[] { method };
  }

  public Route(String path, Controller controller, String method, 
               String method2) {
    this.path = path;
    this.controller = controller;
    this.methods = new String[] { method, method2 };
  }

  public Route(String path, Controller controller, String method, 
               String method2, String method3) {
    this.path = path;
    this.controller = controller;
    this.methods = new String[] { method, method2, method3 };
  }

  public Route(String path, Controller controller, String method, 
               String method2, String method3, String method4) {
    this.path = path;
    this.controller = controller;
    this.methods = new String[] { method, method2, method3, method4 };
  }

  public String getPath() {
    return path;
  }

  public Controller getController() {
    return controller;
  }

  public String[] getMethods() {
    return methods;
  }
}
