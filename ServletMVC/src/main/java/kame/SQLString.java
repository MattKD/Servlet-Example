package kame;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLString implements SQLType {
  private String str;

  public SQLString(String str) {
    this.str = str;
  }

  public String getStr() {
    return str;
  }

  public void setTo(PreparedStatement stmt, int idx) throws SQLException {
    stmt.setString(idx, str);  
  }
}
