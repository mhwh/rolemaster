package dk.hejselbak.weapon;

import java.util.SortedSet;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SortNatural;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlRootElement(name = "weapon")
@Entity
@Table(name="weapon")
public class Weapon implements Comparable<Weapon> {
  @Transient
  private final Logger log = LoggerFactory.getLogger(Weapon.class);

  @Id
  private int id;
  private String name;
  private int fumble;
  
  @Enumerated(EnumType.STRING)
  private WeaponGroup weapon_group;
  
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "weapon_range", joinColumns = @JoinColumn(name = "weapon_id"))
  @SortNatural
  private SortedSet<Range> ranges;

  @Enumerated(EnumType.STRING)
  private ArmorDBModTableLaw armorTableLaw;

  @ManyToOne(fetch=FetchType.LAZY)
  private CritTable critTable;

  private int skinModifier;
  private float skinFactor;
  private int softModifier;
  private float softFactor;
  private int rigidModifier;
  private float rigidFactor;
  private int chainModifier;
  private float chainFactor;
  private int plateModifier;
  private float plateFactor;

  public Weapon() {}

  /**
  * Sorts the weapon after id, where lowest id comes first.
  */
  public int compareTo(Weapon obj) {
    return id - obj.id;
  }

  @XmlElement(name = "id")
	public int getId() {
		return id;
	}

  @XmlElement(name = "name")
	public String getName() {
		return name;
	}

  @XmlElement(name = "group")
	public WeaponGroup getGroup() {
		return weapon_group;
	}

  @XmlElement(name = "fumble")
  public int getFumble() {
		return fumble;
	}

  @XmlElementWrapper(name = "ranges")
  @XmlElement(name = "range")
	public SortedSet<Range> getRanges() {
    log.debug(toString());
		return ranges;
  }

  public ArmorDBModTableLaw getArmorTableLaw() {
    return armorTableLaw;
  }

  public CritTable getCritTable() {
    return critTable;
  }

  public int getSkinModifier() {
    return skinModifier;
  }

  public float getSkinFactor() {
    return skinFactor;
  }

  public int getSoftModifier() {
    return softModifier;
  }

  public float getSoftFactor() {
    return softFactor;
  }

  public int getRigidModifier() {
    return rigidModifier;
  }

  public float getRigidFactor() {
    return rigidFactor;
  }

  public int getChainModifier() {
    return chainModifier;
  }

  public float getChainFactor() {
    return chainFactor;
  }

  public int getPlateModifier() {
    return plateModifier;
  }

  public float getPlateFactor() {
    return plateFactor;
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

  @Override
  public String toString() {
    return "Weapon [armorTableLaw=" + armorTableLaw + ", chainFactor=" + chainFactor + ", chainModifier="
        + chainModifier + ", critTable=" + critTable + ", fumble=" + fumble + ", id=" + id + ", name=" + name
        + ", plateFactor=" + plateFactor + ", plateModifier=" + plateModifier + ", ranges=" + ranges + ", rigidFactor="
        + rigidFactor + ", rigidModifier=" + rigidModifier + ", skinFactor=" + skinFactor + ", skinModifier="
        + skinModifier + ", softFactor=" + softFactor + ", softModifier=" + softModifier + ", weapon_group="
        + weapon_group + "]";
  }

}
