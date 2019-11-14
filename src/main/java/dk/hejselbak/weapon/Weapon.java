package dk.hejselbak.weapon;

import java.util.SortedSet;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlRootElement(name = "Weapon")
public class Weapon implements Comparable<Weapon> {
  private int id;
  private String name;
  private WeaponGroup group;
  private int fumble;
  private SortedSet<Range> ranges;
  private AttackTableEntry[][] table = new AttackTableEntry[20][150];

  public Weapon() {}


  public Weapon(int id, String name, WeaponGroup group, int fumble, SortedSet<Range> ranges) {
    this.id = id;
    this.name = name;
    this.group = group;
    this.fumble = fumble;
    this.ranges = ranges;
  }

  public Weapon(int id, String name, WeaponGroup group, int fumble, AttackTableEntry[][] table, SortedSet<Range> ranges) {
    this(id, name, group, fumble, ranges);
    this.table = table;
  }

  public Weapon(int id, String name, WeaponGroup group, int fumble) {
    this(id, name, group, fumble, new RangeBuilder().build());
  }

  public Weapon(int id, String name, WeaponGroup group, int fumble, AttackTableEntry[][] table) {
    this(id, name, group, fumble);
    this.table = table;
  }
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
		return group;
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
		return ranges;
	}
}
