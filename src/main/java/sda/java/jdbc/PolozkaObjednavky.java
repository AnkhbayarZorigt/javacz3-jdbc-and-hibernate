package sda.java.jdbc;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolozkaObjednavky {
  private Long id;
  private Produkt produkt;
  private BigDecimal jednotkovaCena;
  private Long pocet;

  public BigDecimal getCelkovaCena() {
    return jednotkovaCena.multiply(BigDecimal.valueOf(pocet));
  }
}
