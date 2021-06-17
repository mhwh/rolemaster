package dk.hejselbak.rolemaster.util;

import dk.hejselbak.rolemaster.critical.CriticalType;

public class AttackTableEntryStateCritType extends AttackTableEntryState {

    public AttackTableEntryStateCritType() {
        super("[^a-eA-E]");
    }

    @Override
    protected AttackTableEntryState[] getValidStates() {
        return new AttackTableEntryState[] {HIT_CRIT_SEV, HIT_CRIT_SEV_TYPE, CRIT_SEV, CRIT_SEV_TYPE, CRIT_TYPE, RANGE};
    }

    @Override
    protected void handleBody(AttackTableEntryImpl ate) {
        ate.lastEntryWasRange = false;
        ate.ct = CriticalType.valueOf(ate.currentEntry.toUpperCase());
        // ate.ct Keep the present one!

        addAndUpdate(ate);
    }
}
