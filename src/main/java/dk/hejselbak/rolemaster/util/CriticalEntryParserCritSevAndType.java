package dk.hejselbak.rolemaster.util;

import java.util.Optional;

import dk.hejselbak.rolemaster.critial.CritSeverity;

/**
 * Place description here.
 *
 * @author MHWH@nykredit.dk
 */

public class CriticalEntryParserCritSevAndType extends CriticalEntryParser {
//        } else if (entry.matches("[a-eA-E][^a-eA-E]")) {
//            // CritSev + CritType
//            cs = CritSeverity.valueOf("" + entry.charAt(0));
//            ct = "" + entry.charAt(1);

    public CriticalEntryParserCritSevAndType() {
        super("[a-eA-E][^a-eA-E]");
    }

    @Override
    public Optional<CritSeverity> getCritSev() {
        return Optional.of(CritSeverity.valueOf("" + getEntry().charAt(0)));
    }

    @Override
    public Optional<String> getCritType() {
        return Optional.of("" + getEntry().charAt(1));
    }
}
