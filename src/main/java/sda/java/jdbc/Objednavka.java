package sda.java.jdbc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Objednavka {
  private Long id;
  private LocalDate datum;
  private BigDecimal cena;
  private Zakaznik zakaznik;
  private List<PolozkaObjednavky> polozkaObjednavkyList;
}
