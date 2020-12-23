package dk.hejselbak.rolemaster.weapon;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import dk.hejselbak.rolemaster.critial.CritSeverity;

@XmlRootElement
public class AttackResult {

    private final int hits;
    private CritSeverity severity;
    private SimpleCriticalTable crit;

    @Transient
    public static final AttackResult NO_HIT = new AttackResult(0, null, null);

    public AttackResult(int hits, CritSeverity severity, SimpleCriticalTable crit) {
        this.hits = hits;
        this.severity = severity;
        this.crit = crit;
    }

    @XmlElement
    public int getHits() {
        return hits;
    }

    @XmlElement
    public CritSeverity getSeverity() {
        return severity;
    }

    @XmlElement
    public SimpleCriticalTable getCrit() {
        return crit;
    }

    @Override
    public String toString() {
        return "AttackResult [crit=" + crit + ", hits=" + hits + ", severity=" + severity + "]";
    }
}