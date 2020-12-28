package dk.hejselbak.rolemaster.weapon;

import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;

import java.util.TreeSet;


@QuarkusTest
public class WeaponResourceTest {

    @InjectMock
    WeaponService weaponServiceMock;

    @Test
    public void testWeaponsEndPoint() {
        Mockito.when(weaponServiceMock.getWeapons()).thenReturn(new TreeSet<Weapon>());
        given()
            .when().get("/weapons")
            .then()
                .statusCode(200);
    }

    @Test
    public void testWeaponIdNotFound() {
        Mockito.when(weaponServiceMock.getWeapon(5)).thenReturn(null);
        given()
            .when().get("/weapons/5")
            .then()
                .statusCode(404);
    }
}