package sda.java.jdbc.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.beryx.textio.TextTerminal;
import org.hibernate.Session;

import sda.java.jdbc.model.Produkt;

public class ProductService {
  private final HibernateService hibernateService;

  public ProductService(HibernateService hibernateService) {
    this.hibernateService = hibernateService;
  }

  public void updateProduct(Long index, String name, BigDecimal price) throws SQLException {
    Session session = hibernateService.getSession();
    session.beginTransaction();
    Produkt product = session.get(Produkt.class, index);
    if (name != null && name.length() > 0) {
      product.setNazev(name);
    }
    if (price != null) {
      product.setCena(price);
    }
    session.persist(product);
    session.getTransaction().commit();
  }

  public void deleteProduct(Long index) {
    Session session = hibernateService.getSession();
    session.beginTransaction();
    Produkt product = session.get(Produkt.class, index);
    session.delete(product);
    session.getTransaction().commit();
  }

  public void addProduct(String name, BigDecimal price) {
    Produkt produkt = new Produkt();
    produkt.setNazev(name);
    produkt.setCena(price);
    Session session = hibernateService.getSession();
    session.beginTransaction();
    session.persist(produkt);
    session.getTransaction().commit();
  }

  public void listProduct(TextTerminal textTerminal) {
    hibernateService.getSession().createQuery("SELECT p FROM Produkt p", Produkt.class).getResultStream()
        .forEach(
            it -> textTerminal.println(it.getId() + "  " + it.getNazev() + "  " + it.getCena())
        );
  }


}
