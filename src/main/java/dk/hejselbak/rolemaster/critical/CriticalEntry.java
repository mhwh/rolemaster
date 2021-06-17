package dk.hejselbak.rolemaster.critical;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Contains a entry from a critical table.
 */
@Embeddable
@XmlRootElement
@NoArgsConstructor
@Table
@ToString
public class CriticalEntry implements Comparable<CriticalEntry>  {
    @Id private Long id;
    @XmlElement @Getter @NotNull private Integer max_roll;
    @XmlElement @Getter @Enumerated(EnumType.STRING) CritSeverity severity;
    @XmlElement @Getter private Integer hits;
    @XmlElement @Getter private Integer stun;
    @XmlElement @Getter private Integer cannot_parry_rounds;
    @XmlElement @Getter private Integer must_parry_rounds;
    @XmlElement @Getter private Integer must_parry_penalty;
    @XmlElement @Getter private Integer bleed;
    @XmlElement @Getter private Integer penalty;
    @XmlElement @Getter private Integer bonus;
    @XmlElement @Getter @Column(name="crit_text") private String text;

    @Override
    public int compareTo(CriticalEntry o) {
        int comp = severity.compareTo(o.getSeverity());
        if (comp == 0) {
            comp = max_roll - o.getMax_roll();
        }
        return comp;
    }

}