package dk.hejselbak.rolemaster.weapon;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

/**
* A single range modifier, where the OB modifier is the penalty (+/-) given to the OB.
* The ranges (from and to) are in feet, and both inclusive.
*/
@XmlRootElement(name = "range")
@Embeddable
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Range implements Comparable<Range> {
  @Id @XmlElement(name="id") @Getter private Integer id;
  @NotNull @XmlElement(name = "obModifier") @Getter private Integer obModifier;
  @NotNull @XmlElement(name = "from") @Getter private Integer from_distance;
  @NotNull @XmlElement(name = "to") @Getter private Integer to_distance;
  
  @Override
  public int compareTo(Range obj) {
    return (obj.to_distance + obj.from_distance) > (to_distance + from_distance) ? -1 : 1;
  }  
}
