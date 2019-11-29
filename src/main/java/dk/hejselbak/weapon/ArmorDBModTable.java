package dk.hejselbak.weapon;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "armorDBModTable")
@Entity
public class ArmorDBModTable {
    @Transient
    private Logger log = LoggerFactory.getLogger(ArmorDBModTable.class);

    @Id
    private long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ArmorDBModTableLaw law;
    @NotNull
    private int at;
    @NotNull
    private int mod;

    public long getId() {
        return id;
    }

    @XmlElement(name="attackLaw")
    public ArmorDBModTableLaw getLaw() {
        return law;
    }

    @XmlElement(name="amortype")
    public int getAt() {
        return at;
    }

    @XmlElement(name="modifier")
    public int getMod() {
        return mod;
    }

    @Override
    public String toString() {
        return "ArmorDBModTable [id=" + id + ", at=" + at + ", law=" + law + ", mod=" + mod + "]";
    }

    
}