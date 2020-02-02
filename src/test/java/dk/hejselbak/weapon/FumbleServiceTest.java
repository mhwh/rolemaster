package dk.hejselbak.weapon;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

@QuarkusTest
public class FumbleServiceTest {

    @Inject
    FumbleService service;

    @Test
    public void testGetFumbleTable() {
        FumbleTable ft = service.getFumbleTable(1L);
        assertNotNull(ft);
        assertEquals(1, ft.getId());
        assertEquals("Weapon fumble table", ft.getName());
    }

    @Test
    public void testGetFumbleAt25() {
        FumbleEntry fe = null;
        for (FumbleGroup fg : FumbleGroup.values()) {
            fe = service.getFumble(1L, fg.name(), 25);
            assertNotNull(fe);
            assertEquals(fg, fe.getGroup());
            assertEquals(25, fe.getMax_roll());        
        }
    }

    @Test
    public void testZeroFumble() {
        FumbleEntry fe = null;
        for (FumbleGroup fg : FumbleGroup.values()) {
            fe = service.getFumble(1L, fg.name(), 0);
            
            assertNotNull(fe);
            assertEquals(fg, fe.getGroup());
            assertEquals(25, fe.getMax_roll());        
        }
    }

    @Test
    public void testMinFumble() {
        FumbleEntry fe = null;
        for (FumbleGroup fg : FumbleGroup.values()) {
            fe = service.getFumble(1L, fg.name(), 1);
            
            assertNotNull(fe);
            assertEquals(fg, fe.getGroup());
            assertEquals(25, fe.getMax_roll());        
        }
    }

    @Test
    public void testMaxFumble() {
        FumbleEntry fe = null;
        for (FumbleGroup fg : FumbleGroup.values()) {
            fe = service.getFumble(1L, fg.name(), 100);
            
            assertNotNull(fe);
            assertEquals(fg, fe.getGroup());
            assertEquals(100, fe.getMax_roll());     
        }   
    }

    @Test
    public void testOverMaxFumble() {
        FumbleEntry fe = null;
        for (FumbleGroup fg : FumbleGroup.values()) {
            fe = service.getFumble(1L, fg.name(), 150);
            
            assertNotNull(fe);
            assertEquals(fg, fe.getGroup());
            assertEquals(100, fe.getMax_roll());     
        }   
    }
    @Test
    public void testMiddleFumble() {
        FumbleEntry fe = null;
        for (FumbleGroup fg : FumbleGroup.values()) {
            fe = service.getFumble(1L, fg.name(), 63);
            assertNotNull(fe);
            assertEquals(fg, fe.getGroup());
            assertEquals(70, fe.getMax_roll());        
        }
    }

}