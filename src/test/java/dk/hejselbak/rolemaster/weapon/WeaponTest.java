package dk.hejselbak.rolemaster.weapon;

import dk.hejselbak.rolemaster.weapon.Weapon;
import dk.hejselbak.rolemaster.weapon.WeaponService;
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
    public void testCriticalProxy() {
        result = service.getWeapon(5);
        assertNotNull(result);
        assertNotNull(result.getCritTable());
        assertTrue(result.getCritTable() instanceof SimpleCriticalTable);
    }

}