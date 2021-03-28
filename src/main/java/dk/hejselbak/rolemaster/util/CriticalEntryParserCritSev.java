package dk.hejselbak.rolemaster.util;

import java.util.Optional;

import dk.hejselbak.rolemaster.critial.CritSeverity;

public class CriticalEntryParserCritSev extends CriticalEntryParser {
    //         } else if (entry.matches("[a-eA-E]")) {
    //            //    critSev, brug det sidste hit, og crittye
    //            cs = CritSeverity.valueOf(entry.toUpperCase());
    //        }

    public CriticalEntryParserCritSev() {
        super("[a-eA-E]");
    }

    @Override
    public Optional<CritSeverity> getCritSev() {
        return Optional.of(CritSeverity.valueOf(getEntry().toUpperCase()));
    }
}
