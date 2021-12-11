package sda.java.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.beryx.textio.TextTerminal;

public class ProductService {
  public void updateProduct(Connection con, Long index, String name, Double price) throws SQLException {
    if (name == null && price == null) {
      throw new IllegalArgumentException();
    }
    StringBuilder sql = new StringBuilder("UPDATE produkt SET ");
    if (name != null) {
      sql.append("nazev = ?");
      if (price != null) {
        sql.append(", ");
      }
    }
    if (price != null) {
      sql.append("cena = ?");
    }
    sql.append("WHERE id = ?");
    PreparedStatement stmt = con.prepareStatement(sql.toString());
    int paramIndex = 1;
    if (name != null) {
      stmt.setString(paramIndex, name);
      paramIndex++;
    }
    if (price != null) {
      stmt.setDouble(paramIndex, price);
      paramIndex++;
    }
    stmt.setLong(paramIndex, index);
    int rows  = stmt.executeUpdate();
    stmt.close();
  }

  public void deleteProduct(Connection con, Long index) throws SQLException {
    PreparedStatement stmt = con.prepareStatement("DELETE FROM produkt WHERE id = ?");
    stmt.setLong(1, index);
    int rows  = stmt.executeUpdate();
    stmt.close();
  }

  public void addProduct(Connection con, String name, Double price) throws SQLException {
    PreparedStatement stmt = con.prepareStatement("INSERT INTO produkt (nazev, cena) VALUES (?, ?)");
    stmt.setString(1, name);
    stmt.setDouble(2, price);
    int rows  = stmt.executeUpdate();
    stmt.close();
  }

  public void listProduct(Connection con, TextTerminal textTerminal) throws SQLException {
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("select * from produkt");
    while (rs.next()) {
      textTerminal.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
    }
    stmt.close();
  }


}
