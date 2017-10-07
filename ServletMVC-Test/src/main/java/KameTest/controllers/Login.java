package KameTest.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import kame.Controller;
import KameTest.models.User;

public class Login implements Controller {
  public void doRequest(HttpServletRequest req, HttpServletResponse res)
                        throws ServletException, IOException {
    HttpSession session = req.getSession();
    String name = req.getParameter("name");
    String password = req.getParameter("password");

    if (name == null || name.trim().equals("") ||
        password == null || password.trim().equals("")) {
      String errMsg = "Error: Invalid login data submitted";
      System.out.println(errMsg);
      session.setAttribute("error", errMsg);
      res.sendRedirect("/");
      return;
    } else {
      name = name.trim();
      User user = User.getUserByName(name);
      if (user == null || user.isCorrectPassword(password) == false) {
        String errMsg = "Error: Invalid login data submitted";
        System.out.println(errMsg);
        session.setAttribute("error", errMsg);
        res.sendRedirect("/");
        return;
      }
      String msg = "Logged in as " + name;
      System.out.println(msg);
      session.setAttribute("msg", msg); 
      // use name as a bad way to stay logged in
      session.setAttribute("logged_in", name); 
    }
    res.sendRedirect("/");
  }
}
