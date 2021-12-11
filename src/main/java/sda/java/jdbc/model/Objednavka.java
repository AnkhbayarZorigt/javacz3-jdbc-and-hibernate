package sda.java.jdbc.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Objednavka {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDate datum;
  private BigDecimal cena;

  @ManyToOne
  @JoinColumn(name="id_zakaznik", nullable=false)
  private Zakaznik zakaznik;
  @OneToMany
  @JoinColumn(name="id_objednavky", nullable=false)
  private Set<PolozkaObjednavky> polozkaObjednavkyList;
}
