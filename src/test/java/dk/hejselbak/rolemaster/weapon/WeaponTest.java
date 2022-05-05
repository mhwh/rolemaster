package dk.hejselbak.rolemaster.weapon;

import dk.hejselbak.rolemaster.critical.CritSeverity;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

@QuarkusTest
public class WeaponTest {
    
    @Inject
    WeaponService service;

    Weapon result;
  
    @Test
    public void testLomboks() {
        result = service.getWeapon(0);
        assertNotNull(result);
        assertEquals(0, result.getId());
    }

    @Test
    public void testHit_Middle_1() {
        result = service.getWeapon(0);

        AttackResult ar = result.hit(20, 100);
        assertNotNull(ar);
        assertEquals(2, ar.getHits(), "Did not get the right number of hits.");
        assertTrue(ar.getCrit() == null, "Did not expect a critical on this hit.");
        assertTrue(ar.getSeverity() == null, "Did not expect a critical on this hit.");
    }

    @Test
    public void testHit_Middle_2() {
        testHit(service.getWeapon(0), 10, 142, 11,"P", CritSeverity.D);
    }

    @Test
    public void testHit_Middle_3() {
        testHit(service.getWeapon(0), 12, 121, 4,"P", CritSeverity.A);
    }

    @Test
    public void testHit_OverTheTop() {
        testHit(service.getWeapon(0), 20, 155, 3,"P", CritSeverity.C);
    }

    @Test
    public void testHit_Under() {
        testHit(service.getWeapon(0), 20, 39);
    }

    @Test
    public void testHit_Under_1() {
        testHit(service.getWeapon(1), 20, 29);
    }

    @Test
    public void testHit_Under_2() {
        testHit(service.getWeapon(1), 19, 29);
    }

    @Test
    public void testHit_Directly_1() {
        testHit(service.getWeapon(0), 5, 124, 12,"S", CritSeverity.C);
    }
    @Test
    public void testHit_Directly_2() {
        testHit(service.getWeapon(0), 5, 125, 12, "P", CritSeverity.C);
    }
    @Test
    public void testHit_Directly_3() {
        testHit(service.getWeapon(0), 5, 126, 12,"S", CritSeverity.C);
    }

    private void testHit(Weapon weapon, int at, int roll) {
        AttackResult ar = weapon.hit(at, roll);
        StringBuilder expected = new StringBuilder();
        expected.append(" Roll {AT:").append(at).append(',').append("roll:").append(roll)
            .append(") on the ").append(weapon.getName()).append(" attacktable, must produce a NO_HIT.");

        assertNotNull(ar);
        assertEquals(0, ar.getHits(), expected.toString());
        assertTrue(ar.getCrit() == null, expected.toString());
        assertTrue(ar.getSeverity() == null, expected.toString());
    }

    private void testHit(Weapon weapon, int at, int roll, int expectedHit, String expectedCritTypeShortName, CritSeverity expectedCritSeverity) {
        AttackResult ar = weapon.hit(at, roll);
        StringBuilder expected = new StringBuilder();
        expected.append(" Roll {AT:").append(at).append(',').append("roll:").append(roll)
            .append(") on the ").append(weapon.getName()).append(" attacktable, must produce a ")
            .append(expectedHit).append(expectedCritSeverity.name()).append(expectedCritTypeShortName);

        assertNotNull(ar);
        assertEquals(expectedHit, ar.getHits(), "Did not get the right number of hits. " + expected);
        assertEquals(expectedCritTypeShortName, ar.getCrit().getShort_name(), "Did not get the right critical type. " + expected);
        assertEquals(expectedCritSeverity, ar.getSeverity(), "Did not get the right critical severity. " + expected);
    }
}