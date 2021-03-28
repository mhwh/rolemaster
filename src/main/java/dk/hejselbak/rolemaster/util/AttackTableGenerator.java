package dk.hejselbak.rolemaster.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import dk.hejselbak.rolemaster.critial.CritSeverity;

public class AttackTableGenerator {
    private int at;
    private int range;
    private int hits;
    private CritSeverity cs;
    private String ct; // CritType

    private int weaponId;
    private int attackTableSeq;

    // These must match the critical ID's in the database!
    // S = 1 in the database
    // P = 2
    // ...
    private enum Crit {
        NUL, S, P, K, B
    }

    /**
     * Generates SQL based on the input from the weapon txt file, located in the same directory as this file.
     * @param args First argument is the id of the weaponfile (4.txt means weaponId = 4) to generate.
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Every weapon gets 2000 rows...
        Integer wId = Integer.parseInt(args[0]);
        AttackTableGenerator atg = new AttackTableGenerator(wId, wId * 2000);

        atg.run("src/main/java/dk/hejselbak/rolemaster/util/" + wId + ".txt");
    }

    public AttackTableGenerator(int weaponId, int attackTableSeq) {
        this.weaponId = weaponId;
        this.attackTableSeq = attackTableSeq;
    }

    public void dumpRow() {
        System.out.println(generateInsert());
    }

    public String generateInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into AttackTableEntry(id, weapon_id, at, roll, hits");
        if (cs != null) {
            sb.append(", crit_severity, criticalTable_id");
        }
        sb.append(") values(").
            append(attackTableSeq++).append(',').
            append(weaponId).append(',').
            append(at).append(',').
            append(range).append(',').
            append(hits);
        if (cs != null) {
            sb.append(',').append('\'').append(cs.name()).append('\'').
                append(',').append(Crit.valueOf(ct).ordinal());
        }
        sb.append(");");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "AttackTableGenerator{" +
            "at=" + at +
            ", range=" + range +
            ", hits=" + hits +
            (cs != null ? ", cs=" + cs :"") +
            (ct != null ? ", ct=" + Crit.valueOf(ct).ordinal()  :"") +
            '}';
    }

    public void run(String filename) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            boolean lastEntryWasRange = false;
            int i;
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    // int
                    i = scanner.nextInt();
                    //   >= 40 er det range
                    if (lastEntryWasRange) { // never AT after a range!
                        hits = i;
                        cs = null;
                        ct = null;
                        dumpRow();
                        lastEntryWasRange = false;
                    } else if (i > 20 || scanner.hasNextInt()) { // It is a range, as there will be a int after this, and it will never be a AT
                        range = i;
                        lastEntryWasRange = true;
                    } else { // AT
                        at = i;
                        range = 150;
                        lastEntryWasRange = true;
                    }
                } else {
                    // ! int
                    if (!lastEntryWasRange) range--;
                    handleCritEntry(scanner.next());
                    lastEntryWasRange = false;
                    dumpRow();
                }

            }
        }
    }

    private void handleCritEntry(String entry) {
        Optional<CriticalEntryParser> oParser = Stream.of(CriticalEntryParser.Parsers).filter(e -> e.match(entry)).findFirst();
        if (oParser.isPresent()) {
            CriticalEntryParser parser = oParser.get();
            if (parser.getHits().isPresent()) hits = parser.getHits().get();
            if (parser.getCritSev().isPresent()) cs = parser.getCritSev().get();
            if (parser.getCritType().isPresent()) ct = parser.getCritType().get();
        }
    }
}
