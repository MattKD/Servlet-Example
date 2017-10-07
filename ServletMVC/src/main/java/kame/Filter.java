package kame;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

//
// Must be set as filter-class in webapp/WEB-INF/web.xml.
//
public class Filter implements javax.servlet.Filter {
  private String prefix = "/pages";

  @Override
  public void init(FilterConfig config) {
    System.out.println("KameMVC filter init");
  }

  @Override
  public void doFilter(ServletRequest req, 
                       ServletResponse res,
                       FilterChain chain)
                         throws ServletException, IOException {
    HttpServletRequest http_req = (HttpServletRequest)req;
    String path = http_req.getRequestURI()
      .substring(http_req.getContextPath().length());

    if (path.startsWith("/static")) {
      chain.doFilter(req, res);
    } else {
      RequestDispatcher rd = 
        req.getRequestDispatcher(prefix + path);
      rd.forward(req, res);
    }
  }
}
