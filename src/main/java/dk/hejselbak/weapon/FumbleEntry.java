package dk.hejselbak.weapon;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@XmlRootElement
@NoArgsConstructor
@Table
@ToString
public class FumbleEntry implements Comparable<FumbleEntry> {
    @Id @Getter private Long id; 
    @XmlElement @Enumerated(EnumType.STRING) @Column(name = "fumble_group") @Getter private FumbleGroup group;
    @XmlElement @Getter private Integer max_roll;
    @XmlElement @Getter @Column(name="fumble_text") private String text;

    @Override
    public int compareTo(FumbleEntry o) {
        int comp = o.group.compareTo(getGroup());
        if (comp == 0) {
            comp = max_roll - o.getMax_roll();
        }
        return comp;
    }
}