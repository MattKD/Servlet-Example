package kame;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SQLType {
  void setTo(PreparedStatement stmt, int idx) throws SQLException;
}
