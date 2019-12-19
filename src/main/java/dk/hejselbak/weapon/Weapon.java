package dk.hejselbak.weapon;

import java.util.SortedSet;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SortNatural;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlRootElement(name = "weapon")
@Entity
@Table(name="weapon")
@ToString(exclude="log")
public class Weapon implements Comparable<Weapon> {
  @Transient
  private final Logger log = LoggerFactory.getLogger(Weapon.class);

  @Id
  @XmlElement(name = "id") @Getter private int id;
  @XmlElement(name = "name") @Getter private String name;
  @XmlElement(name = "fumble") @Getter private int fumble;
  
  @Enumerated(EnumType.STRING)
  @XmlElement(name = "group") @Getter private WeaponGroup weaponGroup;
  
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "weapon_range", joinColumns = @JoinColumn(name = "weapon_id"))
  @SortNatural
  @XmlElementWrapper(name = "ranges")
  @XmlElement(name = "range")
  @Getter private SortedSet<Range> ranges;

  @Enumerated(EnumType.STRING)
  @Getter private ArmorDBModTableLaw armorTableLaw;

  @ManyToOne(fetch=FetchType.LAZY)
  @Getter private CritTable critTable;

  @Getter private int skinModifier;
  @Getter private float skinFactor;
  @Getter private int softModifier;
  @Getter private float softFactor;
  @Getter private int rigidModifier;
  @Getter private float rigidFactor;
  @Getter private int chainModifier;
  @Getter private float chainFactor;
  @Getter private int plateModifier;
  @Getter private float plateFactor;

  public Weapon() {}

  /**
  * Sorts the weapon after id, where lowest id comes first.
  */
  public int compareTo(Weapon obj) {
    return id - obj.id;
  }


  public float getATFactor(int at) {
    if (at > 16) {
      return getPlateFactor();
    } else if (at > 12) {
      return getChainFactor();
    } else if (at > 8) {
      return getRigidFactor();
    } else if (at > 4) {
      return getSoftFactor();
    } else {
      return getSkinFactor();
    }
  }

  public int getATModifier(int at) {
    if (at > 16) {
      return getPlateModifier();
    } else if (at > 12) {
      return getChainModifier();
    } else if (at > 8) {
      return getRigidModifier();
    } else if (at > 4) {
      return getSoftModifier();
    } else {
      return getSkinModifier();
    }
  }
}
