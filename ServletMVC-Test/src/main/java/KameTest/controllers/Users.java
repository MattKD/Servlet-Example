package KameTest.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import kame.Controller;
import kame.JSP;
import java.util.ArrayList;
import KameTest.models.User;

public class Users implements Controller {
  public void doRequest(HttpServletRequest req, HttpServletResponse res)
                        throws ServletException, IOException {
    String color = req.getParameter("color");
    ArrayList<User> users;

    if (color == null || color.trim().equals("")) {
      color = "";
      users = User.getUsers(); // null if none
    } else {
      color = color.trim().toLowerCase();
      users = User.getUsersByColor(color);
    }

    req.setAttribute("color", color);
    req.setAttribute("users", users);
    JSP.render("users", req, res);
  }

}
