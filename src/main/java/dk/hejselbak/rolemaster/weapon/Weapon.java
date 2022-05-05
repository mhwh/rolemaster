package dk.hejselbak.rolemaster.weapon;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SortNatural;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlRootElement(name = "Weapon")
@Entity
@Table(name="weapon")
@ToString
@Slf4j
public class Weapon implements Comparable<Weapon> {
  @XmlElement @Getter @Id private int id;
  @XmlElement @Getter private String name;
  @XmlElement @Getter private int fumble;
  
  @Enumerated(EnumType.STRING)
  @XmlElement(name = "group") @Getter private WeaponGroup weaponGroup;
  
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "weapon_range", joinColumns = @JoinColumn(name = "weapon_id"))
  @SortNatural
  @XmlElementWrapper(name = "ranges") @XmlElement(name = "range")
  @Getter private SortedSet<Range> ranges;

  @OneToMany(fetch = FetchType.LAZY, targetEntity=AttackTableEntry.class, mappedBy="weapon")
  @OrderBy("at DESC, roll ASC") // at 20, roll 150 and down through the rolls and at types...
  @XmlElement @Getter private List<AttackTableEntry> attackTable;

  /**
  * Sorts the weapon after id, where lowest id comes first.
  */
  public int compareTo(Weapon obj) {
    return id - obj.id;
  }

  /**
   * Will lookup on the attack table, and return the result.
   *
   * @param at The AT to look up
   * @param roll The roll to look up. If this is > 150, it is set to 150, as the table only goes to 150.
   * @return Will always return a AttackResult, but it can be AttackResult.NO_HIT.
   */
  public AttackResult hit(int at, int roll) {
    AttackResult attackResult = AttackResult.NO_HIT;

    final int tableRoll = (roll > 150 ? 150 : roll);

    Optional<AttackTableEntry> result = attackTable.stream().filter(entry -> entry.getAt() == at && tableRoll <= entry.getRoll()).findFirst();
    if (result.isPresent()) {
      AttackTableEntry ate = result.get();
      attackResult = new AttackResult(ate.getHits(), ate.getCritSeverity(), ate.getCriticalTable());
    }

    if (log.isDebugEnabled()) {
      log.debug("Attack on at " + at + ", roll=" + roll + ". Resultet in : " + attackResult);
    }

    return attackResult;
  }
}
