package dk.hejselbak.rolemaster.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AttackTableGenerator implements AttackTableEntryStateContext {
    private final int weaponId;
    private int attackTableSeq;

    private AttackTableEntryState state;

    @Override
    public void setState(AttackTableEntryState state) {
        this.state = state;
    }

    /**
     * Generates SQL based on the input from the weapon txt file, located in the same directory as this file.
     * @param args First argument is the id of the weapon file (4.txt means weaponId = 4) to generate.
     * @throws FileNotFoundException IF we cant find the file...
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Every weapon gets 2000 rows...
        int wId = Integer.parseInt(args[0]);
        AttackTableGenerator atg = new AttackTableGenerator(wId, wId * 2000);

        atg.run("src/main/java/dk/hejselbak/rolemaster/util/" + wId + ".txt");
    }

    public AttackTableGenerator(int weaponId, int attackTableSeq) {
        this.weaponId = weaponId;
        this.attackTableSeq = attackTableSeq;
    }
    public String createInsert(AttackTableEntry ate) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into AttackTableEntry(id, weapon_id, at, roll, hits");
        if (ate.cs != null) {
            sb.append(", crit_severity, criticalTable_id");
        }
        sb.append(") values(").
            append(attackTableSeq++).append(',').
            append(weaponId).append(',').
            append(ate.at).append(',').
            append(ate.range).append(',').
            append(ate.hits);
        if (ate.cs != null) {
            sb.append(',').append('\'').append(ate.cs.name()).append('\'').
                append(',').append(ate.ct.ordinal());
        }
        sb.append(");");
        return sb.toString();
    }

    public void run(String filename) throws FileNotFoundException {
        AttackTableEntryImpl ate;

        // We always start with the AT
        this.state = new AttackTableEntryStateAT();

        try (Scanner scanner = new Scanner(new File(filename))) {
            ate = new AttackTableEntryImpl(scanner, attackTableSeq, weaponId);

            // Starts the traversal of the entries, and build the list...
            while (state.handle(this, ate));
        }
        for (AttackTableEntry entry : state.getEntries()) {
            System.out.println(createInsert(entry));
        }
    }
}
