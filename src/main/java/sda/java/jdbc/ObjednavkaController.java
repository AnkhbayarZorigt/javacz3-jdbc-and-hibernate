package sda.java.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

public class ObjednavkaController {
  public void executeMenu(ObjednavkaService objednavkaService, TextIO textIO, Connection con) {
    TextTerminal<?> textTerminal = textIO.getTextTerminal();
    loop:
    while (true) {
      try {
        ObjednavkaMenuOptions option = textIO.newEnumInputReader(ObjednavkaMenuOptions.class)
            .read("Uprava objednavek:");
        switch (option) {
          case VYPIS_VSECH:
            objednavkaService.listAllOrders(con, textTerminal);
            break;
          case VYPIS_DETAILU:
            objednavkaService.listAllOrders(con, textTerminal);
            Long index = textIO.newLongInputReader()
                .withMinVal(1l)
                .read("Zadejte id objednavky pro zobrazeni:");
            objednavkaService.listOrder(con, textTerminal, index);
            break;
          case ZPET:
          default:
            break loop;
        }
      } catch (SQLException e) {
        textTerminal.println("Doslo k chybe pri praci s databazi " + e.getMessage());
      }
    }
  }
}
