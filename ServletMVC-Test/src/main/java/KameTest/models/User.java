package KameTest.models;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import kame.SQL;
import kame.SQLType;
import kame.SQLInt;
import kame.SQLString;
import kame.SQLMapper;
import KameTest.Config;

public class User {
  private int id;
  private String name;
  private String password;
  private String salt;
  private String fav_color;

  private User() { }

  public User(String name, String password) {
    // id is -1 for not in table, -2 for in table but id not known
    // postgresql serial is never negative, so this is safe
    this.id = -1; 
    this.name = name;
    this.salt = kame.Util.genSalt(); // 16 len hex string
    this.password = kame.Util.hash(salt + password); // 64 len hex string
    this.fav_color = "";
  }

  public boolean isCorrectPassword(String password) {
    return this.password.equals(kame.Util.hash(salt + password));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setFavColor(String fav_color) {
    this.fav_color = fav_color;
  }

  public String getFavColor() {
    return fav_color == null ? "" : fav_color;
  }

  public int getID() {
    return id;
  }

  public boolean delete() {
    if (getID() == -1) {
      return true;
    }
    SQL sql = new SQL(Config.getDataSource());
    String stmt = "DELETE FROM users WHERE id = ?";
    SQLType[] vals = { new SQLInt(getID()) };
    if (sql.update(stmt, vals) != -1) {
      this.id = -1;
      return true;
    }
    return false;
  }

  private boolean insert() {
    SQL sql = new SQL(Config.getDataSource());
    String stmt = "INSERT INTO users (name, password, salt, fav_color) " +
                  "VALUES (?, ?, ?, ?)";
    SQLType[] vals = { new SQLString(getName()), 
                       new SQLString(password),
                       new SQLString(salt),
                       new SQLString(getFavColor()) };
    if (sql.update(stmt, vals) != -1) {
      id = -2; // saved but id not known
      return true;
    }
    return false;
  }


  private boolean update() {
    SQL sql = new SQL(Config.getDataSource());
    // Check if user is in table but id isn't known
    if (this.id == -2) {
      String stmt = "SELECT id FROM users WHERE name = ?";  
      SQLType[] vals = { new SQLString(getName()) };
      SQLMapper mapper = (ResultSet rset) -> {
        Integer id = rset.getInt(1);
        return id;
      };

      Integer id = sql.<Integer>queryOne(stmt, vals, mapper);
      if (id == null) {
        return false;
      }
      this.id = id;
    }

    String stmt = "UPDATE users SET name = ?, password = ?, salt = ?, " +
                  "fav_color = ? where id = ?";
    SQLType[] vals = { new SQLString(getName()), new SQLString(password), 
                       new SQLString(salt), new SQLString(getFavColor()), 
                       new SQLInt(getID()) };
    return sql.update(stmt, vals) != -1;
  }

  public boolean save() {
    if (id == -1) {
      return insert();
    } else {
      return update();
    }
  }

  public static ArrayList<User> getUsers() {
    SQL sql = new SQL(Config.getDataSource());
    String stmt = "SELECT id, name, password, salt, fav_color " +
                  "FROM users";
    SQLMapper mapper = (ResultSet rset) -> {
      User user = new User();
      user.id = rset.getInt("id");
      user.name = rset.getString(2);
      user.password = rset.getString(3);
      user.salt = rset.getString(4);
      user.fav_color = rset.getString(5);
      return user;
    };

    return sql.query(stmt, mapper);
  }

  private static ArrayList<User> getUsersBy(String field, SQLType val) {
    SQL sql = new SQL(Config.getDataSource());
    String stmt = "SELECT id, name, password, salt, fav_color " +
                  "FROM users where " + field + " = ?";
    SQLType[] vals = { val };
    SQLMapper mapper = (ResultSet rset) -> {
      User user = new User();
      user.id = rset.getInt(1);
      user.name = rset.getString(2);
      user.password = rset.getString(3);
      user.salt = rset.getString(4);
      user.fav_color = rset.getString(5);
      return user;
    };

    return sql.query(stmt, vals, mapper);
  }

  private static User getUserBy(String field, SQLType val) {
    ArrayList<User> users = getUsersBy(field, val);
    if (users != null) {
      return users.get(0);
    }
    return null;
  }

  public static User getUserByID(int id) {
    return getUserBy("id", new SQLInt(id));
  }

  public static User getUserByName(String name) {
    return getUserBy("name", new SQLString(name));
  }

  public static ArrayList<User> getUsersByColor(String color) {
    return getUsersBy("fav_color", new SQLString(color));
  }

}
