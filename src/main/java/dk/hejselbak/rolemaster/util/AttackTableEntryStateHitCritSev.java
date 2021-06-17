package dk.hejselbak.rolemaster.util;

import dk.hejselbak.rolemaster.critical.CritSeverity;

public class AttackTableEntryStateHitCritSev extends AttackTableEntryState {

    public AttackTableEntryStateHitCritSev() {
        super("\\\\d+[a-eA-E]");
    }

    @Override
    protected final AttackTableEntryState[] getValidStates() {
        return new AttackTableEntryState[] {HIT_CRIT_SEV_TYPE, HIT_CRIT_SEV, CRIT_SEV, CRIT_TYPE, CRIT_SEV_TYPE, RANGE, AT};
    }

    @Override
    protected void handleBody(AttackTableEntryImpl ate) {
        ate.hits = Integer.parseInt(ate.currentEntry.split("\\D")[0]);
        ate.lastEntryWasRange = false;
        ate.cs = CritSeverity.valueOf(ate.currentEntry.substring(ate.currentEntry.length()-1).toUpperCase());
        // ate.ct Keep the present one!

        addAndUpdate(ate);
    }
}
