package dk.hejselbak.weapon;

public class CritTable {
  private final String name;

  public CritTable(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static CritTable valueOf(String critTable) {
    CritTable table = null;
    switch (critTable.toUpperCase()) {
      case "B":
        table = new CritTable("Brawling");
        break;
      case "C":
        table = new CritTable("Cold");
        break;
      case "E":
        table = new CritTable("Electrical");
        break;
      case "G":
        table = new CritTable("Grapple");
        break;
      case "H":
        table = new CritTable("Heat");
        break;
      case "I":
        table = new CritTable("Impact");
        break;
      case "K":
        table = new CritTable("Krush");
        break;
      case "L":
        table = new CritTable("Large");
        break;
      case "MS":
        table = new CritTable("Strike");
        break;
      case "MT":
        table = new CritTable("Throw");
        break;
      case "P":
        table = new CritTable("Puncture");
        break;
      case "S":
        table = new CritTable("Slash");
        break;
      case "SN":
        table = new CritTable("Spell Large");
        break;
      case "SS":
        table = new CritTable("Spell Super Large");
        break;
      case "SU":
        table = new CritTable("Super Large");
        break;
      case "T":
        table = new CritTable("Tiny");
        break;
      case "U":
        table = new CritTable("Unbalance");
        break;
      default:
        table = new CritTable("Unknown");
    }
    return table;
  }
}
