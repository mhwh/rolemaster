package dk.hejselbak.weapon;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

/**
* A single range modifier, where the OB modifier is the penalty (+/-) given to the OB.
* The ranges (from and to) are in feet, and both inclusive.
*/
@XmlRootElement(name = "range")
public class Range implements Comparable<Range> {
  private int obModifier;
  private int from;
  private int to;

  public Range(int modifier, int from, int to) {
    this.obModifier = modifier;
    this.from = from;
    this.to = to;
  }

  @XmlElement(name = "obModifier")
  public int getOBModifier() {
    return obModifier;
  }
  @XmlElement(name = "from")
  public int getFrom() {
    return from;
  }
  @XmlElement(name = "to")
  public int getTo() {
    return to;
  }

  public int compareTo(Range obj) {
    return (obj.to + obj.from) > (to + from) ? -1 : 1;
  }
}
