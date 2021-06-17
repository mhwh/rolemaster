package dk.hejselbak.rolemaster.util;

import dk.hejselbak.rolemaster.critical.CritSeverity;
import dk.hejselbak.rolemaster.critical.CriticalType;

public class AttackTableEntryStateCritSevAndType extends AttackTableEntryState {

    public AttackTableEntryStateCritSevAndType() {
        super("[a-eA-E][^a-eA-E]");
    }

    @Override
    protected AttackTableEntryState[] getValidStates() {
        return new AttackTableEntryState[] {CRIT_SEV, CRIT_TYPE, CRIT_SEV_TYPE, HIT_CRIT_SEV_TYPE, HIT_CRIT_SEV, RANGE};
    }

    @Override
    protected void handleBody(AttackTableEntryImpl ate) {
        ate.lastEntryWasRange = false;
        ate.cs = CritSeverity.valueOf("" + ate.currentEntry.charAt(0));
        ate.ct = CriticalType.valueOf("" + ate.currentEntry.charAt(1));

        addAndUpdate(ate);
    }
}
