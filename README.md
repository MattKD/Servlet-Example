# Servlet-Example
Simple MVC framework example on top of Java Servlets and embedded Tomcat

This is an example of a very light mvc framework built on top of Java servlets using an embedded Tomcat server. I made it to learn the Java EE basics. This should not be used in production.

Building requires maven to be installed.

To build ServletMVC:
```
cd ServletMVC
mvn clean install
```

To build ServletMVC-Test:
```
cd ServletMVC-Test
mvn clean package
```

ServletMVC-Test requires a postgres server running at localhost:5432. ServletMVC-Test/src/main/webapp/META-INF/context.xml should be modified to use your username/password. To create the table used in test run:
```
cd ServletMVC-Test
psql -U postgres -d testdb -a -f create-tables.sql
```

To run ServletMVC-Tes:
```
cd ServletMVC-Test
target/bin/run-app
```
