package dk.hejselbak.rolemaster.weapon;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import dk.hejselbak.rolemaster.critial.CritSeverity;
import dk.hejselbak.rolemaster.critial.CritTable;

@XmlRootElement
public class AttackResult {

    private final int hits;
    private CritSeverity severity;
    private CritTable crit;

    public AttackResult(int hits, CritSeverity severity, CritTable crit) {
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
    public CritTable getCrit() {
        return crit;
    }

    @Override
    public String toString() {
        return "AttackResult [crit=" + crit + ", hits=" + hits + ", severity=" + severity + "]";
    }

}