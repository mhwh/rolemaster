package dk.hejselbak.rolemaster.util;

import dk.hejselbak.rolemaster.critical.CritSeverity;
import dk.hejselbak.rolemaster.critical.CriticalType;

public class AttackTableEntry {
    public int at;
    public int range;
    public int hits;
    public CritSeverity cs;
    public CriticalType ct;
    public long attackTableSeq; // The starting ID of the records. This will be incremented everytime this instance is dumped.
    public final int weaponId;

    protected AttackTableEntry(int weaponId, long attackTableSeq) {
        this.weaponId = weaponId;
        this.attackTableSeq = attackTableSeq;
    }

    public AttackTableEntry entry() {
        AttackTableEntry entry = new AttackTableEntry(weaponId, attackTableSeq);
        entry.at = at;
        entry.range = range;
        entry.hits = hits;
        entry.cs = cs;
        entry.ct = ct;

        return entry;
    }

    public void incAttackTableSeq() {
        attackTableSeq++;
    }

    public void decRange() {
        range--;
    }
}
