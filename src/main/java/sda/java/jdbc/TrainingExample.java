package sda.java.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class TrainingExample {

  public static void main(String args[]) {
    try {
      Connection con = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/javacz3", "root", "hUfKTHTjC2X99s4GTFEgUeVy");
      TextIO textIO = TextIoFactory.getTextIO();
      TextTerminal<?> textTerminal = textIO.getTextTerminal();

      ProductService productService = new ProductService();
      ProductController productController = new ProductController();
      ObjednavkaController objednavkaController = new ObjednavkaController();
      ObjednavkaService objednavkaService = new ObjednavkaService();

      loop: while (true) {
        MainMenuOptions option = textIO.newEnumInputReader(MainMenuOptions.class)
            .read("Prace s databazi objednavek:");
        switch (option) {
          case PRODUKT:
            productController.executeMenu(productService, textIO, con);
            break;
          case OBJEDNAVKA:
            objednavkaController.executeMenu(objednavkaService, textIO, con);
            break;
          case KONEC:
          default:
            break loop;
        }
      }

      con.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}
