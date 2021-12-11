package sda.java.jdbc;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Zakaznik {
  private Long id;
  private String jmeno;
  private String telefon;
  private String email;
}
