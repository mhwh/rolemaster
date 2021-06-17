package dk.hejselbak.rolemaster.util;

public class AttackTableEntryStateRange extends AttackTableEntryState {

    public AttackTableEntryStateRange() {
        super("1?[1-9][0-9]");
    }

    @Override
    protected boolean match(AttackTableEntryImpl ate) {
        var returnValue = false;

        try {
            int range = Integer.parseInt(ate.currentEntry);
            returnValue = range > 20 || ate.scanner.hasNextInt();
        } catch (NumberFormatException e) {
            // Nothing, as returnValue is false...
        }

        return returnValue;
    }

    @Override
    protected AttackTableEntryState[] getValidStates() {
        return new AttackTableEntryState[] {HIT, HIT_CRIT_SEV, HIT_CRIT_SEV_TYPE, CRIT_TYPE, CRIT_SEV, CRIT_SEV_TYPE};
    }

    @Override
    public void handleBody(AttackTableEntryImpl ate) {
        ate.range = Integer.parseInt(ate.currentEntry);
        ate.lastEntryWasRange = true;
    }
}
