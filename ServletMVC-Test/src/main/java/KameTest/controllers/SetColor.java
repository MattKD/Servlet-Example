package KameTest.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;
import kame.Controller;
import kame.JSP;
import KameTest.models.User;

public class SetColor implements Controller {
  public void doRequest(HttpServletRequest req, HttpServletResponse res)
                        throws ServletException, IOException {
    HttpSession session = req.getSession();
    String logged_in = (String) session.getAttribute("logged_in");
    if (logged_in == null || logged_in.equals("")) {
      res.sendError(401);
      return;
    }

    String idStr = req.getParameter("id");
    if (idStr == null || idStr.equals("")) {
      res.sendError(401);
      return;
    }

    String color = req.getParameter("color");
    if (color == null || color.trim().equals("")) {
      String errMsg = "Error: Color must not be empty";
      System.out.println(errMsg);
      session.setAttribute("error", errMsg);
      res.sendRedirect("/");
      return;
    } else {
      color = color.trim().toLowerCase();
    }

    try {
      int id = Integer.parseInt(idStr);
      User user = User.getUserByID(id);
      if (user != null) {
        if (user.getName().equals(logged_in)) {
          user.setFavColor(color);
          user.save();
          String msg = "User '" + user.getName() + "' color set to " + color;
          System.out.println(msg);
          session.setAttribute("msg", msg);
          res.sendRedirect("/");
          return;
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    res.sendError(401);
  }

}
