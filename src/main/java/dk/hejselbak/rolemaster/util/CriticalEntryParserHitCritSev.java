package dk.hejselbak.rolemaster.util;

import java.util.Optional;

import dk.hejselbak.rolemaster.critial.CritSeverity;

/**
 * Place description here.
 *
 * @author MHWH@nykredit.dk
 */

public class CriticalEntryParserHitCritSev extends CriticalEntryParser {
    //         } else if (entry.matches("\\d+[a-eA-E]")) {
    //            //    hit + critSev, brug den sidste critType
    //            hits = Integer.parseInt(entry.split("\\D")[0]);
    //            cs = CritSeverity.valueOf(lastChar(entry, 1));

    public CriticalEntryParserHitCritSev() {
        super("\\\\d+[a-eA-E]");
    }

    @Override
    public Optional<Integer> getHits() {
        return Optional.of(Integer.parseInt(getEntry().split("\\D")[0]));
    }

    @Override
    public Optional<CritSeverity> getCritSev() {
        return super.getCritSev();
    }
}
