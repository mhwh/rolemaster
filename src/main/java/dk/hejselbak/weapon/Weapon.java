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
  @CollectionTable(name = "weapon_range", joinColumns = @JoinColumn(name = "weapon_fk"))
/*  @AttributeOverrides({
          @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
          @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
  })*/  
  @SortNatural
  private SortedSet<Range> ranges;

  @Transient private AttackTableEntry[][] table = new AttackTableEntry[20][150];

  public Weapon() {}

  /**
  * Sorts the weapon after id, where lowest id comes first.
  */
  public int compareTo(Weapon obj) {
    return id - obj.id;
  }

  public void setAttackTable(AttackTableEntry[][] table) {
    this.table = table;
  }

  @XmlElement(name = "attackTable")
  public final AttackTableEntry[][] getAttackTable() {
    return table;
  }

  @XmlElement(name = "id")
	public int getId() {
		return id;
	}

	/**
	* Returns value of name
	* @return
	*/
  @XmlElement(name = "name")
	public String getName() {
		return name;
	}

	/**
	* Returns value of group
	* @return
	*/
  @XmlElement(name = "group")
	public WeaponGroup getGroup() {
		return weapon_group;
	}

	/**
	* Returns value of fumble
	* @return
	*/
  @XmlElement(name = "fumble")
  public int getFumble() {
		return fumble;
	}

	/**
	* Returns value of range
	* @return
	*/
  @XmlElementWrapper(name = "ranges")
  @XmlElement(name = "range")
	public SortedSet<Range> getRanges() {
    log.debug(toString());
		return ranges;
  }

  @Override
  public String toString() {
    return "Weapon [fumble=" + fumble + ", id=" + id + ", log=" + log + ", name=" + name + ", ranges=" + ranges
        + ", weapon_group=" + weapon_group + "]";
  }
  
  
}
