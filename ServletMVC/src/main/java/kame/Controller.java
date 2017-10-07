package kame;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

// Implement Controller using Servlet api. Use kame.Route's to connect to paths
// and HTTP methods. Use kame.JSP to render.
public interface Controller {
  void doRequest(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException;
}
