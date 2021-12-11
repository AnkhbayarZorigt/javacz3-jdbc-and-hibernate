package sda.java.jdbc.controller;

import java.sql.SQLException;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import sda.java.jdbc.service.ObjednavkaService;

public class ObjednavkaController {
  private final TextIO textIO;
  private final ObjednavkaService objednavkaService;

  public ObjednavkaController(TextIO textIO, ObjednavkaService objednavkaService) {
    this.textIO = textIO;
    this.objednavkaService = objednavkaService;
  }

  public void executeMenu() {
    TextTerminal<?> textTerminal = textIO.getTextTerminal();
    loop:
    while (true) {
      try {
        ObjednavkaMenuOptions option = textIO.newEnumInputReader(ObjednavkaMenuOptions.class)
            .read("Uprava objednavek:");
        switch (option) {
          case VYPIS_VSECH:
            objednavkaService.listAllOrders(textTerminal);
            break;
          case VYPIS_DETAILU:
            objednavkaService.listAllOrders(textTerminal);
            Long index = textIO.newLongInputReader()
                .withMinVal(1l)
                .read("Zadejte id objednavky pro zobrazeni:");
            objednavkaService.listOrder(textTerminal, index);
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
