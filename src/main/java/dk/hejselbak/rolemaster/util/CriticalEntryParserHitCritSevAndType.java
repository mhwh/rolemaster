package dk.hejselbak.rolemaster.util;

import java.util.Optional;

import dk.hejselbak.rolemaster.critial.CritSeverity;

public class CriticalEntryParserHitCritSevAndType extends CriticalEntryParser {
    public CriticalEntryParserHitCritSevAndType() {
        super("\\d+[a-eA-E][^a-eA-E]");
    }

    @Override
    public Optional<Integer> getHits() {
        return Optional.of(Integer.parseInt(getEntry().split("\\D")[0]));
    }

    @Override
    public Optional<CritSeverity> getCritSev() {
        return Optional.of(CritSeverity.valueOf(lastChar(2)));
    }

    @Override
    public Optional<String> getCritType() {
        return Optional.of(lastChar(1));
    }
}
