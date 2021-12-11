package sda.java.jdbc;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import sda.java.jdbc.controller.MainMenuOptions;
import sda.java.jdbc.controller.ObjednavkaController;
import sda.java.jdbc.controller.ProductController;
import sda.java.jdbc.service.HibernateService;
import sda.java.jdbc.service.ObjednavkaService;
import sda.java.jdbc.service.ProductService;

public class TrainingExample {

  public static void main(String args[]) {
    try {
      TextIO textIO = TextIoFactory.getTextIO();
      TextTerminal<?> textTerminal = textIO.getTextTerminal();

      HibernateService hibernateService = new HibernateService();
      ProductService productService = new ProductService(hibernateService);
      ProductController productController = new ProductController(textIO, productService);
      ObjednavkaService objednavkaService = new ObjednavkaService(hibernateService);
      ObjednavkaController objednavkaController = new ObjednavkaController(textIO, objednavkaService);


      loop: while (true) {
        MainMenuOptions option = textIO.newEnumInputReader(MainMenuOptions.class)
            .read("Prace s databazi objednavek:");
        switch (option) {
          case PRODUKT:
            productController.executeMenu();
            break;
          case OBJEDNAVKA:
            objednavkaController.executeMenu();
            break;
          case KONEC:
          default:
            break loop;
        }
      }

    } catch (Exception e) {
      System.out.println(e);
    }
  }

}
