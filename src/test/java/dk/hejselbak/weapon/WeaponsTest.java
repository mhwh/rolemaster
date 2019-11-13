package dk.hejselbak.weapon;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class WeaponsTest {

    @Test
    public void testSpecificWeaponEndpoint) {
        given()
          .when().get("/weapons/0")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}