package kame;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//
// Light wrapper around DataSource
//
public class SQL {
  private DataSource dataSource;

  public SQL(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public int update(String sql, SQLType[] vals) {
    try (Connection conn = dataSource.getConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        for (int i = 0; i < vals.length; i++) {
          vals[i].setTo(stmt, i+1);
        }
        return stmt.executeUpdate();
      }
    } catch (SQLException e) {
      System.out.println("SQLException thrown!");
      return -1;
    }
  }

  public int update(String sql) {
    try (Connection conn = dataSource.getConnection()) {
      try (Statement stmt = conn.createStatement()) {
        return stmt.executeUpdate(sql);
      }
    } catch (SQLException e) {
      System.out.println("SQLException thrown!");
      return -1;
    }
  }

  public <T> ArrayList<T> query(String sql, SQLType[] vals, 
                                SQLMapper<T> mapper) {
    try (Connection conn = dataSource.getConnection()) {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        for (int i = 0; i < vals.length; i++) {
          vals[i].setTo(stmt, i+1);
        }
        ResultSet rset = stmt.executeQuery();
        ArrayList<T> list = new ArrayList<>();
        while (rset.next()) {
          T obj = mapper.mapNext(rset);
          list.add(obj);
        }
        if (list.isEmpty()) {
          return null;
        }
        return list;
      }
    } catch (SQLException e) {
      System.out.println("SQLException thrown!");
      return null;
    }
  }

  public <T> T queryOne(String sql, SQLType[] vals, SQLMapper<T> mapper) {
    ArrayList<T> objs = query(sql, vals, mapper);
    if (objs != null) {
      return objs.get(0);
    }
    return null;
  }

  public <T> ArrayList<T> query(String sql, SQLMapper<T> mapper) {
    try (Connection conn = dataSource.getConnection()) {
      try (Statement stmt = conn.createStatement()) {
        ResultSet rset = stmt.executeQuery(sql);
        ArrayList<T> list = new ArrayList<>();
        while (rset.next()) {
          T obj = mapper.mapNext(rset);
          list.add(obj);
        }
        if (list.isEmpty()) {
          return null;
        }
        return list;
      }
    } catch (SQLException e) {
      System.out.println("SQLException thrown!");
      return null;
    }
  }

  public <T> T queryOne(String sql, SQLMapper<T> mapper) {
    ArrayList<T> objs = query(sql, mapper);
    if (objs != null) {
      return objs.get(0);
    }
    return null;
  }

  public static DataSource getDataSourceJNDI(String name) {
    String jndiName = "java:/comp/env/" + name;
    DataSource dataSource = null;
    try {
      System.out.println("Getting DataSource: " + name);
      InitialContext ctx  = new InitialContext();
      dataSource = (DataSource) ctx.lookup(jndiName);
    } catch (NamingException e) {
      System.out.println("Error: Couldn't get " + jndiName);
    }
    return dataSource;
  }

}
