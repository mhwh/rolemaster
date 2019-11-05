package dk.hejselbak.weapon;

public class Weapon {
  public int id;
  public String name;
  public WeaponGroup group;
  public int fumble;
  public int range;

  public Weapon() {}

  public Weapon(int id, String name, WeaponGroup group, int fumble, int range) {
    this.id = id;
    this.name = name;
    this.group = group;
    this.fumble = fumble;
    this.range = range;
  }

  public int getId() {
    return id;
  }
}
