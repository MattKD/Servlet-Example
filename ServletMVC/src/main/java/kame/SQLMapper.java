package kame;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SQLMapper<T> {
  T mapNext(ResultSet rset) throws SQLException;  
}
