package dk.hejselbak.rolemaster.player;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@XmlRootElement(name = "Player")
@Entity
@Table(name="player")
@ToString
@Slf4j
public class Player {

    @XmlElement @Getter @Id private int id;
    @XmlElement @Getter private String name;

}
