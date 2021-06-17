package dk.hejselbak.rolemaster.util;

public class AttackTableEntryStateHit extends AttackTableEntryState {

    // 0-999 hits allowed :)
    public AttackTableEntryStateHit() {
        super("[1-9]?[1-9]?[0-9]");
    }

    @Override
    protected final AttackTableEntryState[] getValidStates() {
        return new AttackTableEntryState[] {RANGE, AT};
    }

    @Override
    protected void handleBody(AttackTableEntryImpl ate) {
        ate.hits = Integer.parseInt(ate.currentEntry);
        ate.lastEntryWasRange = false;
        ate.cs = null;
        ate.ct = null;

        addAndUpdate(ate);
    }

    @Override
    protected boolean match(AttackTableEntryImpl ate) {
        // Its ony hits, if the pattern matches AND the last entry was a range!
        return super.match(ate) && ate.lastEntryWasRange;
    }
}
