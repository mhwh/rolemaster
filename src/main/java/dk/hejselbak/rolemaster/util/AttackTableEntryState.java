package dk.hejselbak.rolemaster.util;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AttackTableEntryState {
    private static List<AttackTableEntry> entries = new ArrayList<>();
    private final String matchPattern;
    // List of the states available...
    protected static final AttackTableEntryState HIT = new AttackTableEntryStateHit();
    protected static final AttackTableEntryState AT = new AttackTableEntryStateAT();
    protected static final AttackTableEntryState HIT_CRIT_SEV = new AttackTableEntryStateHitCritSev();
    protected static final AttackTableEntryState HIT_CRIT_SEV_TYPE = new AttackTableEntryStateHitCritSevAndType();
    protected static final AttackTableEntryState CRIT_SEV = new AttackTableEntryStateCritSev();
    protected static final AttackTableEntryState CRIT_TYPE = new AttackTableEntryStateCritType();
    protected static final AttackTableEntryState CRIT_SEV_TYPE = new AttackTableEntryStateCritSevAndType();
    protected static final AttackTableEntryState RANGE = new AttackTableEntryStateRange();

    public AttackTableEntryState(String matchPattern) {
        this.matchPattern = matchPattern;
    }

    public final List<AttackTableEntry> getEntries() {
        return entries;
    }

    /**
     * This implementation matches the next token in the scanner!
     * Do what neds to be done. Then locate and send the memento to the next parser who accepts...
     * @param ate The state body to tag along every state...
     * @return true if thie implementation accepts the token, in order for the caller to stop the iteration ...
     */
    public final boolean handle(AttackTableEntryStateContext context, AttackTableEntryImpl ate) {
        var returnValue = false;

        try {
            handleBody(ate);
            // Next token...
            if (ate.scanner.hasNext()) {
                ate.currentEntry = ate.scanner.next();
                for (AttackTableEntryState state : getValidStates()) {
                    if (state == null) {
                        log.error("WTF: " + ate);
                    } else if (state.match(ate)) {
                        context.setState(state);
                        returnValue = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception in Handle: " + ate);
        }
        return returnValue;
    }

    /** 
     * @return A list of valid states from this instance! 
    */
    protected abstract AttackTableEntryState[] getValidStates();

    /**
     * PRE: The pattern of the instance matches the next token from ate
     */
    protected abstract void handleBody(AttackTableEntryImpl ate);

    protected boolean match(AttackTableEntryImpl ate) {
        return ate.currentEntry.matches(getMatchPattern());
    }

    protected final String getMatchPattern() {
        return matchPattern;
    }

    protected final void addAndUpdate(AttackTableEntryImpl entry) {
        entries.add(entry.entry());
        entry.incAttackTableSeq();
        entry.decRange(); // Auto dec. range, making it possible to skip the range every time...
    }
}
