package dk.hejselbak.rolemaster.util;

import dk.hejselbak.rolemaster.critical.CritSeverity;

public class AttackTableEntryStateCritSev extends AttackTableEntryState {

    public AttackTableEntryStateCritSev() {
        super("[a-eA-E]");
    }

    @Override
    protected AttackTableEntryState[] getValidStates() {
        return new AttackTableEntryState[] {HIT_CRIT_SEV, HIT_CRIT_SEV_TYPE, CRIT_SEV_TYPE, CRIT_TYPE, CRIT_SEV, RANGE};
    }

    @Override
    protected void handleBody(AttackTableEntryImpl ate) {
        ate.lastEntryWasRange = false;
        ate.cs = CritSeverity.valueOf(ate.currentEntry.toUpperCase());

        addAndUpdate(ate);
    }
}
