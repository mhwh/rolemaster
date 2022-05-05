package dk.hejselbak.rolemaster.util;

import dk.hejselbak.rolemaster.critical.CritSeverity;
import dk.hejselbak.rolemaster.critical.CriticalType;

public class AttackTableEntryStateHitCritSevAndType extends AttackTableEntryState {

    public AttackTableEntryStateHitCritSevAndType() {
        super("\\d+[a-eA-E][^a-eA-E]");
    }

    @Override
    protected final AttackTableEntryState[] getValidStates() {
        return new AttackTableEntryState[] {CRIT_SEV, CRIT_TYPE, CRIT_SEV_TYPE, HIT_CRIT_SEV, HIT_CRIT_SEV_TYPE, RANGE};
    }

    @Override
    protected void handleBody(AttackTableEntryImpl ate) {
        ate.hits = Integer.parseInt(ate.currentEntry.split("\\D")[0]);
        ate.cs = CritSeverity.valueOf(ate.currentEntry.substring(ate.currentEntry.length()-2, ate.currentEntry.length()-1).toUpperCase());
        ate.ct = CriticalType.valueOf(ate.currentEntry.substring(ate.currentEntry.length()-1).toUpperCase());

        ate.lastEntryWasRange = false;

        addAndUpdate(ate);
    }
}
