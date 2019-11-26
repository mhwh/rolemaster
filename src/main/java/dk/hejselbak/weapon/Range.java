package dk.hejselbak.weapon;

import javax.xml.bind.annotation.XmlRootElement;

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
public class Range implements Comparable<Range> {
  @Id
  private int id;
  @NotNull
  private int obModifier;
  @NotNull
  private int from_distance;
  @NotNull
  private int to_distance;
  
  public Range() {
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

  @Override
  public String toString() {
    return "Range [id=" + id + ", obModifier=" + obModifier + ", from_distance=" + from_distance + ", to_distance=" + to_distance + "]";
  }

  
}
