package dk.hejselbak.weapon;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "armorDBModTable")
@Entity
@ToString
@Slf4j
public class ArmorDBModTable {
    @Id @Getter
    private long id;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @XmlElement(name="attackLaw")
    @Getter 
    private ArmorDBModTableLaw law;
    
    @NotNull
    @XmlElement(name="amortype")
    @Getter
    private int at;
    
    @NotNull
    @Getter
    @XmlElement(name="modifier")
    private int mod;

}