package dk.hejselbak.weapon;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

@QuarkusTest
public class WeaponTest {
    
    @Inject
    WeaponService service;

    Weapon result;
  
    @BeforeAll
    public void loadWeapon() {
        result = service.getWeapon(0);
        assertNotNull(result);
    }

    @Test
    public void testLomboks() {
        //result = service.getWeapon(0);
        //assertNotNull(result);
        assertEquals(0, result.getId());
    } 

}