package sda.java.jdbc;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produkt {
  private Long id;
  private String nazev;
  private BigDecimal cena;
}
