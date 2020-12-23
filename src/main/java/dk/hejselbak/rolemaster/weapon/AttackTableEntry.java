package dk.hejselbak.rolemaster.weapon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import dk.hejselbak.rolemaster.critial.CritSeverity;
import lombok.Getter;

@XmlRootElement(name = "AttackTableEntry")
@Entity( name = "attacktableentry")
public class AttackTableEntry {
    @Id private long id;
    @ManyToOne private Weapon weapon;
    @Getter private int at;
    @Getter private int roll;
    @Getter @NotNull private int hits;
    @Getter @Enumerated(EnumType.STRING) @Column(name = "crit_severity") private CritSeverity critSeverity;
    @Getter @ManyToOne(fetch= FetchType.LAZY) private SimpleCriticalTable criticalTable;
}
