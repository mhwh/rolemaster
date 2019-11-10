package dk.hejselbak.weapon;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "attackTableResult")
public class AttackTableEntry {
    private final int hits;
    private final CritSeverity critSeverity;
    private final CritTable critTable;

    public static final AttackTableEntry NO_HIT = new AttackTableEntry(0);

  	/**
  	* Default AttackTableEntry constructor
  	*/
  	public AttackTableEntry(int hits, CritSeverity critSeverity, CritTable critTable) {
  		this.hits = hits;
  		this.critSeverity = critSeverity;
  		this.critTable = critTable;
  	}

    public AttackTableEntry(int hits) {
      this.hits = hits;
      this.critSeverity = null;
      this.critTable = null;
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
}
