package dk.hejselbak.rolemaster.util;

import java.util.Optional;

public class CriticalEntryParserCritType extends CriticalEntryParser {
    //         } else if (entry.matches("[^a-eA-E]")) {
    //            //    critType, brug den sidste critSev og hit
    //            ct = entry.toUpperCase();

    public CriticalEntryParserCritType() {
        super("[^a-eA-E]");
    }

    @Override
    public Optional<String> getCritType() {
        return Optional.of(getEntry().toUpperCase());
    }
}
