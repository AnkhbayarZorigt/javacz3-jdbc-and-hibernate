package sda.java.jdbc.controller;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import sda.java.jdbc.service.ProductService;

public class ProductController {

  private final TextIO textIO;
  private final ProductService productService;

  public ProductController(TextIO textIO, ProductService productService) {
    this.textIO = textIO;
    this.productService = productService;
  }

  public void executeMenu() {
    TextTerminal<?> textTerminal = textIO.getTextTerminal();
    loop:
    while (true) {
      try {
        ProductMenuOptions option = textIO.newEnumInputReader(ProductMenuOptions.class)
            .read("Uprava informaci o produktech:");
        switch (option) {
          case VYPIS_VSECH_PRODUKTU:
            productService.listProduct(textTerminal);
            break;
          case PRIDANI_PRODUKTU: {
            String name = textIO.newStringInputReader()
                .withMinLength(1)
                .withInputTrimming(true)
                .read("Nazev produktu:");
            Double price = textIO.newDoubleInputReader()
                .withMinVal(0.0)
                .read("Cena produktu:");
            productService.addProduct(name, BigDecimal.valueOf(price));
            break;
          }
          case ZMENA_PRODUKTU: {
            productService.listProduct(textTerminal);
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
            productService.updateProduct(index, name, BigDecimal.valueOf(price));
            break;
          }
          case SMAZANI_PRODUKTU: {
            productService.listProduct(textTerminal);
            Long index = textIO.newLongInputReader()
                .withMinVal(1l)
                .read("Cislo radku ke smazani:");
            productService.deleteProduct(index);
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
