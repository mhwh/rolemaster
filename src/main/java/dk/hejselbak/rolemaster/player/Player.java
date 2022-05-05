package dk.hejselbak.rolemaster.player;

import java.util.SortedSet;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.SortNatural;

@XmlRootElement(name = "Player")
@Entity
@Table(name="player")
@ToString
public class Player {

    @XmlElement @Getter @Id private int id;
    @XmlElement @Getter private String name;
    @XmlElement @Getter private int hits;
    @XmlElement @Getter private int at;
    @XmlElement @Getter private int db;

    @ElementCollection(targetClass=PlayerWeapon.class)
    @SortNatural
    @XmlElementWrapper(name = "playerWeapons") @XmlElement(name = "playerWeapon")
    @Getter private SortedSet<PlayerWeapon> weaponskill;
}
