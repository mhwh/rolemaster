package dk.hejselbak.rolemaster.weapon;

import java.util.SortedSet;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import dk.hejselbak.rolemaster.critial.CriticalEntry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SortNatural;

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
