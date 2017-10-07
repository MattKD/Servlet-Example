package KameTest;

import javax.sql.DataSource;
import kame.Route;
import KameTest.controllers.Index;
import KameTest.controllers.UserPage;
import KameTest.controllers.Users;
import KameTest.controllers.AddUser;
import KameTest.controllers.DeleteUser;
import KameTest.controllers.Login;
import KameTest.controllers.Logout;
import KameTest.controllers.SetColor;

public class Config implements kame.Config {
  private static DataSource dataSource;

  public static DataSource getDataSource() {
    return dataSource;
  }

  // Your custom init
  public void init() {
    // Get a postgres datasource using JNDI
    dataSource = kame.SQL.getDataSourceJNDI("jdbc/postgres"); 
  }

  // Connect paths to controllers. "GET" is default. 
  public Route[] getRoutes() {
    Route[] routes = {
      new Route("/", new Index()),
      new Route("/users", new Users()),
      new Route("/add_user", new AddUser(), "POST"),
      new Route("/delete_user", new DeleteUser(), "POST"),
      new Route("/login", new Login(), "POST"),
      new Route("/logout", new Logout(), "GET", "POST"),
      new Route("/user", new UserPage()),
      new Route("/set_color", new SetColor(), "POST"),
    };
    return routes;
  }

}
