package dk.hejselbak.weapon;

import java.util.SortedSet;

public class Weapon implements Comparable<Weapon> {
  private int id;
  private String name;
  private WeaponGroup group;
  private int fumble;
  private SortedSet<Range> ranges;

  public Weapon() {}

  public Weapon(int id, String name, WeaponGroup group, int fumble, SortedSet<Range> ranges) {
    this.id = id;
    this.name = name;
    this.group = group;
    this.fumble = fumble;
    this.ranges = ranges;
  }

  public Weapon(int id, String name, WeaponGroup group, int fumble) {
    this(id, name, group, fumble, new RangeBuilder().build());
  }

  /**
  * Sorts the weapon after id, where lowest id comes first.
  */
  public int compareTo(Weapon obj) {
    return id - obj.id;
  }

	public int getId() {
		return id;
	}

	/**
	* Returns value of name
	* @return
	*/
	public String getName() {
		return name;
	}

	/**
	* Returns value of group
	* @return
	*/
	public WeaponGroup getGroup() {
		return group;
	}

	/**
	* Returns value of fumble
	* @return
	*/
  public int getFumble() {
		return fumble;
	}

	/**
	* Returns value of range
	* @return
	*/
	public SortedSet<Range> getRanges() {
		return ranges;
	}
}
