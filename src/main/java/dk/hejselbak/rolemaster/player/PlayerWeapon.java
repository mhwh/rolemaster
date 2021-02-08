package dk.hejselbak.rolemaster.player;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import dk.hejselbak.rolemaster.weapon.Range;
import dk.hejselbak.rolemaster.weapon.Weapon;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name="weaponskill")
@ToString
@Slf4j
public class PlayerWeapon implements Comparable<PlayerWeapon> {
    @XmlElement
    @Getter
    @Id
    private int id;
    @XmlElement @Getter private int ob;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "weapon_id", referencedColumnName = "id")
    @XmlElement @Getter
    @ManyToOne
    private Weapon weapon;

    @Override
    public int compareTo(PlayerWeapon playerWeapon) {
        if (playerWeapon.ob > this.ob) return -1;
        if (playerWeapon.ob < this.ob) return 1;
        return 0;
    }
}
