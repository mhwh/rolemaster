package dk.hejselbak.rolemaster.util;

import java.util.Scanner;

public class AttackTableEntryImpl extends AttackTableEntry {
    public final Scanner scanner;
    public String currentEntry;
    public boolean lastEntryWasRange;

    public AttackTableEntryImpl(Scanner scanner, long attackTableSeq, int weaponId) {
        super(weaponId, attackTableSeq);
        this.scanner = scanner;
        this.currentEntry = scanner.next();
        at = 0;
        range = 0;
        hits = 0;
        cs = null;
        ct = null;
        lastEntryWasRange = false;
    }

    @Override
    public String toString() {
        return "AttackTableEntryImpl{" +
                "at=" + at +
                ", range=" + range +
                ", hits=" + hits +
                ", cs=" + cs +
                ", ct=" + ct +
                ", attackTableSeq=" + attackTableSeq +
                ", weaponId=" + weaponId +
                ", currentEntry='" + currentEntry + '\'' +
                ", lastEntryWasRange=" + lastEntryWasRange +
                '}';
    }
}
