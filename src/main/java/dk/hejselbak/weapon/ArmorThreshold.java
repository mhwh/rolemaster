package dk.hejselbak.weapon;

public class ArmorThreshold {
    private static final int SKIN = 0;
    private static final int SOFT = 1;
    private static final int RIGID = 2;
    private static final int CHAIN = 3;
    private static final int PLATE = 4;
    private static int[] ATTYPES = {SKIN, SKIN, SKIN, SKIN, // 1-4
                                    SOFT, SOFT, SOFT, SOFT, // 5-8
                                    RIGID, RIGID, RIGID, RIGID, // 9-12
                                    CHAIN, CHAIN, CHAIN, CHAIN, // 13-16
                                    PLATE, PLATE, PLATE, PLATE}; // 17-20
    private static final int[][] THRESHOLD = {{61, 71, 81, 91, 101, 111}, // SKIN (THT, A, B, C, D, E)
                                              {52, 70, 82, 94, 106, 118}, // SOFT
                                              {50, 80, 95, 110, 125, 140}, // RIGID
                                              {26, 69, 86, 103, 120, 137}, // CHAIN
                                              {5, 65, 85, 105, 125, 145}}; // PLATE

    public static boolean isHit(int roll, int at) {
        return roll >= THRESHOLD[ATTYPES[at-1]][0];
    }

    public static int getTHT(int roll, int at) {
        return THRESHOLD[ATTYPES[at-1]][0];
    }

    public static boolean isCrit(int roll, int at) {
        return roll >= THRESHOLD[ATTYPES[at-1]][1];
    }

    public static CritSeverity getCritSeverity(int roll, int at) {
        CritSeverity result = null;

        if (roll >= THRESHOLD[ATTYPES[at-1]][5]) {
            result = CritSeverity.E;
        } else if (roll >= THRESHOLD[ATTYPES[at-1]][4]) {
            result = CritSeverity.D;
        } else if (roll >= THRESHOLD[ATTYPES[at-1]][3] ) {
            result = CritSeverity.C;
        } else if (roll >= THRESHOLD[ATTYPES[at-1]][2]) {
            result = CritSeverity.B;
        } else if (roll >= THRESHOLD[ATTYPES[at-1]][1]) {
            result = CritSeverity.A;
        }
        return result;
    }
}