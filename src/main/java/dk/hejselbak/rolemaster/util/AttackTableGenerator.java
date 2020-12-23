package dk.hejselbak.rolemaster.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import dk.hejselbak.rolemaster.critial.CritSeverity;

public class AttackTableGenerator {
    private int at;
    private int range;
    private int hits;
    private CritSeverity cs;
    private String ct; // CritType

    private int weapon_id;
    private int attackTable_seq;

    private enum Crit {
        S, P, K, B
    }

    public static void main(String[] args) throws FileNotFoundException {
        int w = 2;
        // Every weapon gets 2000 rows...
        AttackTableGenerator atg = new AttackTableGenerator(w, w * 2000);

        atg.run("src/main/java/dk/hejselbak/rolemaster/" + w + ".txt");
    }

    public AttackTableGenerator(int weapon_id, int attackTable_seq) {
        this.weapon_id = weapon_id;
        this.attackTable_seq = attackTable_seq;
    }

    public void dumpRow() {
        System.out.println(generateInsert());
    }

    public String generateInsert() {
        // insert into AttackTable(id, weapon_id, at, roll, hit, crit_severity, crit_type) values(1, 0, 1, 95, 8, 'A', 'S');
        StringBuilder sb = new StringBuilder();
        sb.append("insert into AttackTableEntry(id, weapon_id, at, roll, hits");
        if (cs != null) {
            sb.append(", crit_severity, criticalTable_id");
        }
        sb.append(") values(").
            append(attackTable_seq++).append(',').
            append(weapon_id).append(',').
            append(at).append(',').
            append(range).append(',').
            append(hits);
        if (cs != null) {
            sb.append(',').append('\'').append(cs.name()).append('\'').
                append(',').append(Crit.valueOf(ct).ordinal()+1);
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
            (ct != null ? ", ct=" + Crit.valueOf(ct).ordinal()+1  :"") +
            '}';
    }

    public void run(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        boolean lastEntryWasRange = false;
        String entry;
        int i;
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                // int
                i = scanner.nextInt();
                //   >= 40 er det range
                if (i >= 21) {
                    range = i;
                    lastEntryWasRange = true;
                } else {
                    //   vis det kommer efter en range er det et hit!
                    if (lastEntryWasRange) {
                        hits = i;
                        cs = null;
                        ct = null;
                        dumpRow();
                        lastEntryWasRange = false;
                    } else {
                        //   hvis det IKKE kommer efter en range er det AT
                        at = i;
                        range = 150;
                        lastEntryWasRange = true;
                    }
                }
            } else {
                // ! int
                entry = scanner.next();
                if (!lastEntryWasRange) {
                    range--;
                }

                if (entry.matches("\\d+[a-eA-E][^a-eA-E]")) {
                    //    hit + CritSev + CritType
                    hits = Integer.parseInt(entry.split("\\D")[0]);
                    cs = CritSeverity.valueOf("" + entry.charAt(entry.length()-2));
                    ct = "" + entry.charAt(entry.length()-1);
                    ct.toUpperCase();
                } else if (entry.matches("[a-eA-E][^a-eA-E]")) {
                    // CritSev + CritType
                    cs = CritSeverity.valueOf(""+entry.charAt(0));
                    ct = "" + entry.charAt(1);
                    ct.toUpperCase();
                } else if (entry.matches("\\d+[a-eA-E]")) {
                    //    hit + critSev, brug den sidste critType
                    hits = Integer.parseInt(entry.split("\\D")[0]);
                    cs = CritSeverity.valueOf("" + entry.charAt(entry.length()-1));
                } else if (entry.matches("[^a-eA-E]")) {
                    //    critType, brug den sidste critSev og hit
                    ct = entry.toUpperCase();
                } else if (entry.matches("[a-eA-E]")) {
                    //    critSev, brug det sidste hit, og crittye
                    cs = CritSeverity.valueOf(entry.toUpperCase());
                }

                lastEntryWasRange = false;
                dumpRow();
            }

        }
    }
}
