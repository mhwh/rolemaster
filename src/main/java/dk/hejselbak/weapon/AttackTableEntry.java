package dk.hejselbak.weapon;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "attackTableEntry")
@Entity
public class AttackTableEntry {
  @Transient
  private final Logger log = LoggerFactory.getLogger(AttackTableEntry.class);

  @Id
  private long id;
  private int armourtype;
  private int roll;
  @NotNull
  private int hits;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "crit_severity")
  private CritSeverity critSeverity;

  @ManyToOne(fetch=FetchType.LAZY)
  private CritTable critTable;

  @ManyToOne
  private Weapon weapon;

  @Transient
  public static final AttackTableEntry NO_HIT = new AttackTableEntry(0);

  private AttackTableEntry(int hits) {
    this.id = -1;
    this.hits = hits;
  }

  public AttackTableEntry() {
  }

  @XmlElement
  public int getArmourType() {
    return armourtype;
  }

  @XmlElement
  public int getRoll() {
    return roll;
  }

  @XmlElement(name="hits")
  public int getHits() {
    return hits;
  }

  @XmlElement(name="severity")
  public CritSeverity getCritSeverity() {
    return critSeverity;
  }

  @XmlElement(name="table")
  public CritTable getCritTable() {
    return critTable;
  }

  @Override
  public String toString() {
    return "AttackTableEntry [id=" + id + ", armourtype=" + armourtype + ", roll=" + roll + ", hits=" + hits + ", critSeverity=" + critSeverity + ", critTable=" + critTable + "]";
  }

  
}
