package dk.hejselbak.weapon;

import java.util.SortedSet;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SortNatural;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@XmlRootElement
@NoArgsConstructor
@ToString
public class FumbleTable {

    @Id @Getter private Long id;
    @XmlElement @Getter private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "FumbleEntry", joinColumns = @JoinColumn(name = "fumbleTable_id"))
    @SortNatural
    @XmlElementWrapper(name = "entries") @XmlElement(name = "entry")
    @Getter private SortedSet<FumbleEntry> entries;
      
}