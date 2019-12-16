package dk.hejselbak.weapon;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class WeaponResourceTest {
    
    @Test
    public void testWeaponsEndPoint() {
        given()
            .when().get("/weapons")
            .then()
                .statusCode(200);
    }

    @Test
    public void testWeaponId() {
        given()
            .when().get("/weapons/5")
            .then()
                .statusCode(200)
                .body(is("{\"armorTableLaw\":\"AL\",\"chainFactor\":10.4,\"chainModifier\":0,\"critTable\":{\"id\":11,\"name\":\"Puncture\",\"shortName\":\"P\"},\"fumble\":4,\"group\":\"ONE_HANDED_SLASHING\",\"id\":5,\"name\":\"Rapier\",\"plateFactor\":23.7,\"plateModifier\":18,\"ranges\":[],\"rigidFactor\":9.1,\"rigidModifier\":-4,\"skinFactor\":5.3,\"skinModifier\":10,\"softFactor\":7.5,\"softModifier\":5}"));
    }

    @Test
    public void testWeaponIdZeroId() {
        given()
            .when().get("/weapons/0")
            .then()
                .statusCode(200)
                .body(is("{\"armorTableLaw\":\"AL\",\"chainFactor\":10.7,\"chainModifier\":-20,\"critTable\":{\"id\":11,\"name\":\"Puncture\",\"shortName\":\"P\"},\"fumble\":1,\"group\":\"ONE_HANDED_SLASHING\",\"id\":0,\"name\":\"Dagger\",\"plateFactor\":22.9,\"plateModifier\":-20,\"ranges\":[{\"OBModifier\":-10,\"from\":1,\"id\":0,\"to\":10},{\"OBModifier\":-20,\"from\":11,\"id\":0,\"to\":25},{\"OBModifier\":-30,\"from\":26,\"id\":0,\"to\":50}],\"rigidFactor\":8.5,\"rigidModifier\":-20,\"skinFactor\":4.7,\"skinModifier\":-10,\"softFactor\":8.2,\"softModifier\":-12}"));
    }

    @Test
    public void testInvalidWeaponIdToBigPositiveId() {
        given()
            .when().get("/weapon/99999")
            .then()
                .statusCode(404)
                .body(containsString("Could not find resource"));
    }

    @Test
    public void testInvalidWeaponIdToSmallNegativeId() {
        given()
            .when().get("/weapon/-99999")
            .then()
                .statusCode(404)
                .body(containsString("Could not find resource"));
    }

    @Test
    public void testInvalidAtOnHit_AtZero() {
        given()
            .when().get("/weapons/0/hit?modifiedRoll=10&at=0")
            .then()
                .statusCode(400)
                .body(containsString("'at' parameter is mandatory, and must be in the interval [1..20]"));
    }

    @Test
    public void testInvalidAtOnHit_At21() {
        given()
            .when().get("/weapons/0/hit?modifiedRoll=10&at=21")
            .then()
                .statusCode(400)
                .body(containsString("'at' parameter is mandatory, and must be in the interval [1..20]"));
    }

    @Test
    public void testValidAtOnHit_At1() {
        given()
            .when().get("/weapons/0/hit?modifiedRoll=100&at=1")
            .then()
                .statusCode(200);
    }

    @Test
    public void testValidAtOnHit_At20() {
        given()
            .when().get("/weapons/0/hit?modifiedRoll=100&at=20")
            .then()
                .statusCode(200);
    }

    @Test
    public void testMissingModifiedRollParameterOnHit() {
        given()
            .when().get("/weapons/0/hit?at=20")
            .then()
                .statusCode(400);
    }

    @Test
    public void testInvalidModifiedRollOnHit_negative() {
        given()
            .when().get("/weapons/0/hit?at=20&modifiedRoll=-1")
            .then()
                .statusCode(400);
    }

    @Test
    public void testValidModifiedRollOnHit_NoOutcome() {
        given()
            .when().get("/weapons/0/hit?at=20&modifiedRoll=10")
            .then()
                .statusCode(200)
                .body(is("{\"hits\":-2}"));
    }
}