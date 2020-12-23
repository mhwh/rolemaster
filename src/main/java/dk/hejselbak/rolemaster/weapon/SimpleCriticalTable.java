package dk.hejselbak.rolemaster.weapon;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="critical_table")
@XmlRootElement(name = "CritTable")
@NoArgsConstructor
@ToString
public class SimpleCriticalTable {
  @Id @Getter private long id;
  @NotNull @XmlElement(name="name") @Getter private String name;
  @NotNull @XmlElement(name="short_name") @Getter private String short_name;
}
