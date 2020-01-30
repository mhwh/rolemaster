package dk.hejselbak.weapon;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

@QuarkusTest
public class CriticalServiceTest {

    @Inject
    CriticalService cs;

    @Test
    public void testGetCriticalTable() {
        CritTable ct = cs.getCritTable("S");
        assertNotNull(ct);
        assertEquals(1, ct.getId());
        assertEquals("S", ct.getShort_name());
        assertEquals("Slash", ct.getName());
    }


    @Test
    public void testGetCriticalMinimum() {
        CriticalEntry ce = cs.getCritcal("S", "A", 1);
        assertNotNull(ce);
        assert(ce.getHits() == null);
    }

    @Test
    public void testGetCriticalMaximum() {
        CriticalEntry ce = cs.getCritcal("S", "E", 100);
        assertNotNull(ce);
        assert(ce.getHits() != null);
        assertEquals(10, ce.getHits());
        assertEquals(12, ce.getStun());
        assertEquals(12, ce.getCannot_parry_rounds());
    }

    @Test
    public void testGetCriticalMiddle() {
        CriticalEntry ce = cs.getCritcal("S", "C", 66);
        assertNotNull(ce);
        assert(ce.getHits() != null);
        assertEquals(6, ce.getHits());
        assert(ce.getStun() == null);
        assertEquals(3, ce.getCannot_parry_rounds());
        assertEquals(-90, ce.getPenalty());
    }
}