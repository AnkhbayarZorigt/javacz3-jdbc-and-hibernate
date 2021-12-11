package sda.java.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beryx.textio.TextTerminal;

public class ObjednavkaService {

  Map<Long, Zakaznik> zakaznikMap = new HashMap<>();

  public void listAllOrders(Connection con, TextTerminal<?> textTerminal) throws SQLException {
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("select * from objednavka");
    while (rs.next()) {
      textTerminal.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
    }
    stmt.close();

  }

  public void listOrder(Connection con, TextTerminal<?> textTerminal, Long index) throws SQLException {
    Objednavka objednavka = getOrder(con, index);
    textTerminal.println("Objednavka " + index);
    textTerminal.println(objednavka.getDatum().toString() + " " + objednavka.getCena().toString() + " " + objednavka.getZakaznik().getJmeno());
    textTerminal.println("Polozky:");
    for (PolozkaObjednavky polozkaObjednavky: objednavka.getPolozkaObjednavkyList()) {
      textTerminal.println(polozkaObjednavky.getId() + "  " + polozkaObjednavky.getProdukt().getNazev() + "  " + polozkaObjednavky.getPocet() + "  " + polozkaObjednavky.getJednotkovaCena().toString() + "  " + polozkaObjednavky.getCelkovaCena().toString());
    }
  }

  public Objednavka getOrder(Connection con, Long index) throws SQLException {
    Objednavka objednavka = null;
    PreparedStatement stmt = con.prepareStatement("SELECT * FROM objednavka WHERE id = ?");
    stmt.setLong(1, index);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
      objednavka = new Objednavka();
      objednavka.setId(rs.getLong("id"));
      objednavka.setCena(rs.getBigDecimal("cena"));
      objednavka.setDatum(rs.getDate("datum").toLocalDate());
      objednavka.setZakaznik(getZakaznik(con, rs.getLong("id_zakaznik")));
      objednavka.setPolozkaObjednavkyList(getItemsForOrder(con, index));
    }
    stmt.close();
    return objednavka;
  }

  private List<PolozkaObjednavky> getItemsForOrder(Connection con, Long index) throws SQLException {
    List<PolozkaObjednavky> polozkaObjednavkyList = new ArrayList<>();
    PreparedStatement stmt = con.prepareStatement("SELECT * FROM polozka_objednavky WHERE id_objednavka = ?");
    stmt.setLong(1, index);
    ResultSet rs = stmt.executeQuery();
    while (rs.next()) {
      PolozkaObjednavky polozkaObjednavky = new PolozkaObjednavky();
      polozkaObjednavky.setId(rs.getLong("id"));
      polozkaObjednavky.setPocet(rs.getLong("pocet"));
      polozkaObjednavky.setJednotkovaCena(rs.getBigDecimal("cena"));
      polozkaObjednavky.setProdukt(getProdukt(con, rs.getLong("id_produkt")));
      polozkaObjednavkyList.add(polozkaObjednavky);
    }
    stmt.close();
    return polozkaObjednavkyList;
  }

  private Produkt getProdukt(Connection con, long index) throws SQLException {
    Produkt produkt = null;
    PreparedStatement stmt = con.prepareStatement("SELECT * FROM produkt WHERE id = ?");
    stmt.setLong(1, index);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
      produkt = new Produkt();
      produkt.setId(rs.getLong("id"));
      produkt.setNazev(rs.getString("nazev"));
      produkt.setCena(rs.getBigDecimal("cena"));
    }
    stmt.close();
    return produkt;
  }

  private Zakaznik getZakaznik(Connection con, long index) throws SQLException {
    Zakaznik zakaznik = null;
    PreparedStatement stmt = con.prepareStatement("SELECT * FROM zakaznik WHERE id = ?");
    stmt.setLong(1, index);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
      if (zakaznikMap.containsKey(index)) {
        zakaznik = zakaznikMap.get(index);
      } else {
        zakaznik = new Zakaznik();
      }
      zakaznik.setId(rs.getLong("id"));
      zakaznik.setJmeno(rs.getString("jmeno"));
      zakaznik.setEmail(rs.getString("email"));
      zakaznik.setTelefon(rs.getString("telefon"));
    }
    stmt.close();
    zakaznikMap.put(index, zakaznik);
    return zakaznik;
  }
}
