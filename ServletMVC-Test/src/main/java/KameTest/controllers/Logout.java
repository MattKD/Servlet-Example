package KameTest.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import kame.Controller;
import KameTest.models.User;

public class Logout implements Controller {
  public void doRequest(HttpServletRequest req, HttpServletResponse res)
                        throws ServletException, IOException {
    HttpSession session = req.getSession();
    String user = (String) session.getAttribute("logged_in");
    if (user != null) {
      System.out.println("Logged out from '" + user + "'");
      session.removeAttribute("logged_in");
      session.setAttribute("msg", "Logged out");
    }
    res.sendRedirect("/");
  }
}
