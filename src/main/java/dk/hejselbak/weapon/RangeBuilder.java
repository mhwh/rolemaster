package dk.hejselbak.weapon;

import java.util.SortedSet;
import java.util.TreeSet;

public class RangeBuilder {
  private SortedSet<Range> ranges = new TreeSet<Range>();

  public RangeBuilder() {  }

  public RangeBuilder addRange(int ob, int from, int to) {
    ranges.add(new Range(ob, from, to));
    return this;
  }

  public SortedSet<Range> build() {
    return ranges;
  }
}
