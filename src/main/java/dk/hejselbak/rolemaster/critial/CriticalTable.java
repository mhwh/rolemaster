package dk.hejselbak.rolemaster.critial;

import java.util.SortedSet;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SortNatural;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="critical_table")
@XmlRootElement(name = "CritTable")
@NoArgsConstructor
@ToString
public class CriticalTable {
  @Id @Getter private long id;
  @NotNull @XmlElement(name="name") @Getter private String name;
  @NotNull @XmlElement(name="short_name") @Getter private String short_name;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "CriticalEntry", joinColumns = @JoinColumn(name = "criticalTable_id"))
  @SortNatural
  @XmlElementWrapper(name = "entries") @XmlElement(name = "entry")
  @Getter private SortedSet<CriticalEntry> entries;

  @XmlElement(name = "numberSeverities")
  public int getNumberSeverities() {
    int count = 0;
    CritSeverity lastSeverity = null;

    if (entries != null && entries.size() > 0) {
      lastSeverity = entries.first().getSeverity();
      count++;
      for (CriticalEntry entry : entries) {
        if (!lastSeverity.equals(entry.getSeverity())) {
          lastSeverity = entry.getSeverity();
          count++;
        }
      }
    }
    return count;
  }

}
