package dk.hejselbak.weapon;

public class AttackTableEntry {
    private final int hits;
    private final CritSeverity critSeverity;
    private final CritTable critTable;

  	/**
  	* Default AttackTableEntry constructor
  	*/
  	public AttackTableEntry(int hits, CritSeverity critSeverity, CritTable critTable) {
  		this.hits = hits;
  		this.critSeverity = critSeverity;
  		this.critTable = critTable;
  	}

    public int getHits() {
      return hits;
    }

    public CritSeverity getCritSeverity() {
      return critSeverity;
    }
}
