package dk.hejselbak.rolemaster.weapon;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "armorDBModTable")
@Entity
@ToString
public class ArmorDBModTable {
    @Id @Getter private long id;
    
    @NotNull @Enumerated(EnumType.STRING) @XmlElement(name="attackLaw")
    @Getter private ArmorDBModTableLaw law;
    
    @NotNull @XmlElement(name="amortype") @Getter private int at;
    @NotNull @XmlElement(name="modifier") @Getter private int mod;
}