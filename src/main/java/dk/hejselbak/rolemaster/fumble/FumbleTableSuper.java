package dk.hejselbak.rolemaster.fumble;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "FumbleTable") 
@XmlRootElement
@NoArgsConstructor
@ToString
public class FumbleTableSuper {

    @Id @Getter private Long id;
    @XmlElement @Getter private String name;
}