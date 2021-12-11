package sda.java.jdbc.service;

import java.sql.SQLException;

import org.beryx.textio.TextTerminal;

import sda.java.jdbc.model.Objednavka;
import sda.java.jdbc.model.PolozkaObjednavky;

public class ObjednavkaService {

  private final HibernateService hibernateService;

  public ObjednavkaService(HibernateService hibernateService) {
    this.hibernateService = hibernateService;
  }

  public void listAllOrders(TextTerminal<?> textTerminal) throws SQLException {
    hibernateService.getSession().createQuery("SELECT o FROM Objednavka o", Objednavka.class).getResultStream().forEach(
        it -> textTerminal.println(it.getId() + " " + it.getDatum().toString() + " " + it.getZakaznik().getJmeno() + " " + it.getCena().toString())
    );
  }

  public void listOrder(TextTerminal<?> textTerminal, Long index) {
    Objednavka objednavka = getOrder(index);
    textTerminal.println("Objednavka " + index);
    textTerminal.println(objednavka.getDatum().toString() + " " + objednavka.getCena().toString() + " " + objednavka.getZakaznik().getJmeno());
    textTerminal.println("Polozky:");
    for (PolozkaObjednavky polozkaObjednavky: objednavka.getPolozkaObjednavkyList()) {
      textTerminal.println(polozkaObjednavky.getId() + "  " + polozkaObjednavky.getProdukt().getNazev() + "  " + polozkaObjednavky.getPocet() + "  " + polozkaObjednavky.getJednotkovaCena().toString() + "  " + polozkaObjednavky.getCelkovaCena().toString());
    }
  }

  public Objednavka getOrder(Long index) {
    return hibernateService.getSession().get(Objednavka.class, index);
  }

}
