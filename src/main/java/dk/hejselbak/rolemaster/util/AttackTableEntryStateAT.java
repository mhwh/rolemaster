package dk.hejselbak.rolemaster.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AttackTableEntryStateAT extends AttackTableEntryState {

    // Only accepts 1-20 AT numbers.
    public AttackTableEntryStateAT() {
        super("20|1[0-9]|[1-9]");
    }

    @Override
    protected final AttackTableEntryState[] getValidStates() {
        // It can be
        //  - Hits only
        //  - Hits + crit (type and sev)
        return new AttackTableEntryState[] {HIT_CRIT_SEV_TYPE, HIT};
    }

    @Override
    protected void handleBody(AttackTableEntryImpl ate) {
        ate.hits = 0;
        ate.cs = null;
        ate.ct = null;
        ate.range = 150; // We set the range to 150 when starting a new AT!
        ate.lastEntryWasRange = true; // We just changed the range ...

        // The entry is a number in the range 1-20.
        ate.at = Integer.parseInt(ate.currentEntry);
    }

    @Override
    protected boolean match(AttackTableEntryImpl ate) {
        // Never AT after a range!
        // If not after a range, and the pattern matches, the next must not be a int.
        return !ate.lastEntryWasRange && super.match(ate) && !ate.scanner.hasNextInt();
    }
}
