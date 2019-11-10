package dk.hejselbak.weapon;

public class AttackTableBuilder {
  private AttackTableEntry[][] table = new AttackTableEntry[20][150];

  /**
   * (10, 98, 5, "C", "P") => [10][98] = 5 hits, B Puncture crit.
   */
  public AttackTableBuilder addEntry(int at, int roll, int hit, String severity, String critTable) {
    table[at-1][roll-1] = new AttackTableEntry(hit, CritSeverity.valueOf(severity), CritTable.valueOf(critTable));;
    return this;
  }

  public AttackTableBuilder addEntry(int at, int roll, int hit) {
    table[at-1][roll-1] = new AttackTableEntry(hit);
    return this;
  }

  public AttackTableEntry[][] build() {
    return table;
  }
}
