package kame;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLInt implements SQLType {
  private int num;

  public SQLInt(int num) {
    this.num = num;
  }

  public int getNum() {
    return num;
  }

  public void setTo(PreparedStatement stmt, int idx) throws SQLException {
    stmt.setInt(idx, num);  
  }
}
