package dk.hejselbak.weapon;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

/**
* A single range modifier, where the OB modifier is the penalty (+/-) given to the OB.
* The ranges (from and to) are in feet, and both inclusive.
*/
@XmlRootElement(name = "range")
@Entity
@Table(name="weapon_range")
public class Range implements Comparable<Range> {
  @Transient
  private final Logger log = LoggerFactory.getLogger(Range.class);

  @Id
  private int id;
  private int obModifier;
  private int from_distance;
  private int to_distance;
  
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="weapon_fk", nullable = false)  
  private Weapon weapon;

  public Range() {
    log.debug("Empty constructor.");
  }

  public Range(int id, int modifier, int from, int to, Weapon weapon) {
    log.debug("Weapon constructor.");
    this.id = id;
    this.obModifier = modifier;
    this.from_distance = from;
    this.to_distance = to;
    this.weapon = weapon;
  }

  public Range(int modifier, int from, int to) {
    log.debug("Minimal constructor.");
    this.obModifier = modifier;
    this.from_distance = from;
    this.to_distance = to;
  }

  @XmlElement(name="id")
  public int getId() {
    return id;
  }

  @XmlElement(name = "obModifier")
  public int getOBModifier() {
    return obModifier;
  }
  @XmlElement(name = "from")
  public int getFrom() {
    return from_distance;
  }
  @XmlElement(name = "to")
  public int getTo() {
    return to_distance;
  }

  @Override
  public int compareTo(Range obj) {
    return (obj.to_distance + obj.from_distance) > (to_distance + from_distance) ? -1 : 1;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + from_distance;
    result = prime * result + id;
    result = prime * result + obModifier;
    result = prime * result + to_distance;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Range other = (Range) obj;
    if (from_distance != other.from_distance)
      return false;
    if (id != other.id)
      return false;
    if (obModifier != other.obModifier)
      return false;
    if (to_distance != other.to_distance)
      return false;
    return true;
  }

  
}
