package KameTest.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;
import kame.Controller;
import kame.JSP;
import java.util.ArrayList;
import KameTest.models.User;

public class AddUser implements Controller {
  public void doRequest(HttpServletRequest req, HttpServletResponse res)
                        throws ServletException, IOException {
    HttpSession session = req.getSession();
    String name = req.getParameter("name");
    String password = req.getParameter("password");
    if (name == null || name.trim().equals("") ||
        password == null || password.trim().equals("")) {
      String errMsg = "Error: Invalid User data submitted";
      System.out.println(errMsg);
      session.setAttribute("error", errMsg);
    } else {
      name = name.trim();
      User user = new User(name, password);
      if (!user.save()) {
        String errMsg = "Error: Couldn't create user '" + name + "'";
        System.out.println(errMsg);
        session.setAttribute("error", errMsg);
      } else {
        String msg = "User '" + user.getName() + "' successfully created!";
        System.out.println(msg);
        session.setAttribute("msg", msg);
        // use name as a bad way to stay logged in
        session.setAttribute("logged_in", name); 
      }
    }
    res.sendRedirect("/");
  }

}
