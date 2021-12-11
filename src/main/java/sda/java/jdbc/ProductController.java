package sda.java.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

public class ProductController {

  public void executeMenu(ProductService product, TextIO textIO, Connection con) {
    TextTerminal<?> textTerminal = textIO.getTextTerminal();
    loop:
    while (true) {
      try {
        ProductMenuOptions option = textIO.newEnumInputReader(ProductMenuOptions.class)
            .read("Uprava informaci o produktech:");
        switch (option) {
          case VYPIS_VSECH_PRODUKTU:
            product.listProduct(con, textTerminal);
            break;
          case PRIDANI_PRODUKTU: {
            String name = textIO.newStringInputReader()
                .withMinLength(1)
                .withInputTrimming(true)
                .read("Nazev produktu:");
            Double price = textIO.newDoubleInputReader()
                .withMinVal(0.0)
                .read("Cena produktu:");
            product.addProduct(con, name, price);
            break;
          }
          case ZMENA_PRODUKTU: {
            product.listProduct(con, textTerminal);
            Long index = textIO.newLongInputReader()
                .withMinVal(1l)
                .read("Cislo radku ke zmene:");
            String name = textIO.newStringInputReader()
                .withDefaultValue(null)
                .withMinLength(-1)
                .withInputTrimming(true)
                .read("Nazev produktu:");
            Double price = textIO.newDoubleInputReader()
                .withDefaultValue(null)
                .withMinVal(0.0)
                .read("Cena produktu:");
            if (name != null && name.trim().isEmpty()) {
              name = null;
            }
            product.updateProduct(con, index, name, price);
            break;
          }
          case SMAZANI_PRODUKTU: {
            product.listProduct(con, textTerminal);
            Long index = textIO.newLongInputReader()
                .withMinVal(1l)
                .read("Cislo radku ke smazani:");
            product.deleteProduct(con, index);
            break;
          }
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
