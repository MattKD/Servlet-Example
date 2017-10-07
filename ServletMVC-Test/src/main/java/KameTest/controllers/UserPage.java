package KameTest.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;
import kame.Controller;
import kame.JSP;
import KameTest.models.User;

public class UserPage implements Controller {
  public void doRequest(HttpServletRequest req, HttpServletResponse res)
                        throws ServletException, IOException {
    HttpSession session = req.getSession();
    String name = req.getParameter("name");
    User user = null;
    boolean wasError = false;

    if (name == null || name.trim().equals("")) {
      wasError = true;
    } else {
      name = name.trim();
      user = User.getUserByName(name);
      if (user == null) {
        wasError = true;
      }
    }

    if (wasError) {
      String errMsg = "Error: Invalid username";
      System.out.println(errMsg);
      session.setAttribute("error", errMsg);
      res.sendRedirect("/");
      return;
    }

    String logged_in = (String)session.getAttribute("logged_in");
    if (logged_in != null && logged_in.equals(name)) {
      req.setAttribute("home_page", new Boolean(true));
    }
    req.setAttribute("user", user);
    JSP.render("user", req, res);
  }

}
