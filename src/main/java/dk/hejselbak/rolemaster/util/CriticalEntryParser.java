package dk.hejselbak.rolemaster.util;

import java.util.Optional;

import dk.hejselbak.rolemaster.critial.CritSeverity;

public abstract class CriticalEntryParser {
    private final String matchPattern;
    private String entry;

    public static CriticalEntryParser[] Parsers = new CriticalEntryParser[] {
        new CriticalEntryParserHitCritSevAndType(),
        new CriticalEntryParserCritSevAndType(),
        new CriticalEntryParserHitCritSev(),
        new CriticalEntryParserCritType(),
        new CriticalEntryParserCritSev()};

    public CriticalEntryParser(String matchPattern) {
        this.matchPattern = matchPattern;
    }

    public boolean match(String entry) {
        this.entry = entry;
        return entry.matches(matchPattern);
    }

    protected String getEntry() {
        return entry;
    }

    protected String lastChar(int offset) {
        StringBuilder sb = new StringBuilder();
        sb.append(getEntry().charAt(getEntry().length() - offset));
        return sb.toString().toUpperCase();
    }

    public Optional<Integer> getHits() {
        return Optional.empty();
    }

    public Optional<CritSeverity> getCritSev() {
        return Optional.empty();
    }

    public Optional<String> getCritType() {
        return Optional.empty();
    }
}
