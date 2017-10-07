package kame;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSP {

  public static void render(String jsp, HttpServletRequest req, 
                            HttpServletResponse res)
      throws ServletException, IOException {

    RequestDispatcher rd = 
        req.getRequestDispatcher("/WEB-INF/jsp/" + jsp + ".jsp");
    rd.forward(req, res);
  }
}


