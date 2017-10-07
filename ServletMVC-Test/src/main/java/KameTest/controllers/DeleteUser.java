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

public class DeleteUser implements Controller {
  public void doRequest(HttpServletRequest req, HttpServletResponse res)
                        throws ServletException, IOException {
    HttpSession session = req.getSession();
    String logged_in = (String) session.getAttribute("logged_in");
    if (logged_in == null || logged_in.equals("")) {
      res.sendError(401);
      return;
    }

    String idStr = req.getParameter("id");

    try {
      int id = Integer.parseInt(idStr);
      User user = User.getUserByID(id);
      if (user != null) {
        if (user.getName().equals(logged_in)) {
          user.delete();
          String msg = "User '" + user.getName() + "' successfully deleted!";
          System.out.println(msg);
          session.setAttribute("msg", msg);
          session.removeAttribute("logged_in");
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
