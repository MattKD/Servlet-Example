package KameTest.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;
import kame.Controller;
import kame.JSP;
import KameTest.models.User;

public class Index implements Controller {
  public void doRequest(HttpServletRequest req, HttpServletResponse res)
                        throws ServletException, IOException {
    HttpSession session = req.getSession();
    String msg = (String)session.getAttribute("msg");
    if (msg != null) {
      session.removeAttribute("msg");
      req.setAttribute("msg", msg);
    }

    String err = (String)session.getAttribute("error");
    if (err != null) {
      session.removeAttribute("error");
      req.setAttribute("error", err);
    }

    String username = (String)session.getAttribute("logged_in");
    if (username != null) {
      User user = User.getUserByName(username);
      req.setAttribute("user", user);
    }

    JSP.render("index", req, res);
  }
}
