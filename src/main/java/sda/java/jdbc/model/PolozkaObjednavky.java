package sda.java.jdbc.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sda.java.jdbc.model.Produkt;

@Entity
@Table(name = "polozka_objednavky")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolozkaObjednavky {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "cena")
  private BigDecimal jednotkovaCena;

  private Long pocet;

  @ManyToOne
  private Produkt produkt;

  public BigDecimal getCelkovaCena() {
    return jednotkovaCena.multiply(BigDecimal.valueOf(pocet));
  }
}
