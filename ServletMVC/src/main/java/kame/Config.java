package kame;

// Class to be implemented and set as init-param "config" in 
// webapp/WEB-INF/web.xml. init can be used for global initialization
// that depends on tomcat being initialized, such as using JNDI to get 
// a datasource. getRoutes returns array of Route's connecting URL paths to
// your Controllers.
public interface Config {
  void init();
  Route[] getRoutes();
}
