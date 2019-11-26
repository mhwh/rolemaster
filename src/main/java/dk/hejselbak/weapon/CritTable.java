package dk.hejselbak.weapon;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="critical_table")
@XmlRootElement(name = "CritTable")
public class CritTable {
  @Id
  private long id;
  @NotNull
  private String name;
  @NotNull
  private String short_name;

  public CritTable() {}

  @XmlElement(name="name")
  public String getName() {
    return name;
  }

  @XmlElement(name="short_name")
  public String getShortName() {
    return short_name;
  }

  @Override
  public String toString() {
    return "CritTable [id=" + id + ", name=" + name + ", short_name=" + short_name + "]";
  }
}
