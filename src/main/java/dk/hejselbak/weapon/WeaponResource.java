package dk.hejselbak.weapon;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

@Path("/weapons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(info=
  @Info(
    title="Rolemaster Weapon service",
    version="0.1",
    description="Service used to obtain information about weapontypes in Rolemaster. This yelds information from the ArmsLaw rulebook.",
    contact=@Contact(name="Developer", email="michael@weber-hansen.dk")
  ),
  servers=@Server(url="http://localhost:8080", description="Development server")
  )
public class WeaponResource {

  private SortedSet<Weapon> weapons = new TreeSet<Weapon>();

  public WeaponResource() {
    initiateWeapons();
  }

  @GET
  public SortedSet<Weapon> list() {
    return weapons;
  }

  @GET
  @Path("/{id}")
  @Operation(summary = "Locate a specific weapon",
    description = "Returns a weapon, with the given id",
    responses = {
      @ApiResponse(responseCode = "200", description = "The weapon", content = @Content(schema = @Schema(implementation = Weapon.class))),
      @ApiResponse(responseCode = "404", description = "Weapon not found, with the given id")
    })
  public Weapon getWeapon(@PathParam("id") int id) {
    for (Weapon w : weapons) {
      if (w.getId() == id) {
        return w;
      }
    }
    throw new NotFoundException();
  }

  @GET
  @Path("/{id}/hit")
  @Operation(summary = "Looks up the result of a hit with the specific weapon on the specific AT.",
    description = "Returns a attack result, that can be 0, a number of hits, or a number of hits and a crit (severity and type)",
    responses = {
      @ApiResponse(responseCode = "200", description = "The attack result", content = @Content(schema = @Schema(implementation = AttackTableEntry.class))),
      @ApiResponse(responseCode = "400", description = "Invalid parameters, either at or modifiedRoll are not valid."),
      @ApiResponse(responseCode = "404", description = "Weapon not found, with the given id")
    })
  public AttackTableEntry hit(@PathParam("id") int id, @QueryParam("at") Integer at, @QueryParam("modifiedRoll") Integer roll) {
    Weapon weapon = getWeapon(id);

    if (at == null || at < 1 || at > 20) {
      throw new WebApplicationException(
        Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
          .entity("'at' parameter is mandatory, and must be in the interval [1..20]")
          .build()
      );
    }
    if (roll == null || roll <= weapon.getFumble() || roll > 150) {
      throw new WebApplicationException(
        Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
          .entity("'modifiedRoll' parameter is mandatory. It must be 150 or below, and above the fumble of the chosen weapon (" + weapon.getName() + " fumble is " + weapon.getFumble() + ").")
          .build()
      );
    }

    AttackTableEntry[][] table = getWeapon(id).getAttackTable();
    AttackTableEntry result;

    do {
      result = table[at-1][roll-1];
      roll--;
    } while (result == null && roll > 1);

    return (result != null) ? result : AttackTableEntry.NO_HIT;
  }

  private void initiateWeapons() {
    AttackTableEntry[][] table;

    table = new AttackTableBuilder().addEntry(1, 90, 7).addEntry(1, 95, 8, "A", "S").addEntry(1, 150, 18, "E", "S").
      addEntry(20, 40, 1).addEntry(20, 77, 2).addEntry(20, 114, 3).addEntry(20, 145, 3, "A", "K").addEntry(20, 146, 3, "A", "P").addEntry(20, 149, 3, "B", "P").addEntry(20, 150, 3, "C", "P").
      addEntry(19, 50, 1).addEntry(19, 75, 2).addEntry(19, 100, 3).addEntry(19, 125, 4).addEntry(19, 140, 4, "A", "K").addEntry(19, 141, 4, "A", "P").addEntry(19, 146, 4, "B", "P").addEntry(19, 149, 4, "C", "P").
      build();
    weapons.add(new Weapon(0, "Dagger", WeaponGroup.ONE_HANDED_SLASHING, 1, table, new RangeBuilder().addRange(-10, 1, 10).addRange(-20, 11, 25).addRange(-30, 26, 50).build()));

    table = new AttackTableBuilder().addEntry(1, 85, 8, "A", "K").addEntry(1, 150, 34, "E", "S").addEntry(20, 30, 1).addEntry(20, 64, 5).addEntry(20, 150, 14, "E", "K").build();
    weapons.add(new Weapon(1, "Falchion", WeaponGroup.ONE_HANDED_SLASHING, 5, table));
    weapons.add(new Weapon(2, "Hand axe", WeaponGroup.ONE_HANDED_SLASHING, 4, new RangeBuilder().addRange(-15, 1, 10).addRange(-90, 51, 100).addRange(-30, 11, 25).addRange(-45, 26, 50).build()));
    weapons.add(new Weapon(3, "Maine Gauche", WeaponGroup.ONE_HANDED_SLASHING, 2, new RangeBuilder().addRange(-15, 1, 10).build()));
    weapons.add(new Weapon(4, "Scimitar", WeaponGroup.ONE_HANDED_SLASHING, 4));
    weapons.add(new Weapon(5, "Rapier", WeaponGroup.ONE_HANDED_SLASHING, 4));
    weapons.add(new Weapon(6, "Broadsword", WeaponGroup.ONE_HANDED_SLASHING, 3));
    weapons.add(new Weapon(7, "Short Sword", WeaponGroup.ONE_HANDED_SLASHING, 2, new RangeBuilder().addRange(-30, 1, 10).build()));
    weapons.add(new Weapon(8, "Armored fist", WeaponGroup.ONE_HANDED_CONCUSSION, 1));
    weapons.add(new Weapon(9, "Club", WeaponGroup.ONE_HANDED_CONCUSSION, 4, new RangeBuilder().addRange(-40, 1, 10).build()));
    weapons.add(new Weapon(10, "War Hammer", WeaponGroup.ONE_HANDED_CONCUSSION, 4, new RangeBuilder().addRange(-20, 1, 10).addRange(-40, 11, 25).addRange(-60, 26, 50).build()));
    weapons.add(new Weapon(11, "Mace", WeaponGroup.ONE_HANDED_CONCUSSION, 2, new RangeBuilder().addRange(-35, 1, 10).build()));
    weapons.add(new Weapon(12, "Morning star", WeaponGroup.ONE_HANDED_CONCUSSION, 8));
    weapons.add(new Weapon(13, "Whip", WeaponGroup.ONE_HANDED_CONCUSSION, 6));
    weapons.add(new Weapon(14, "Bola", WeaponGroup.MISSILE, 7, new RangeBuilder().addRange(0, 1, 50).addRange(-20, 51, 100).addRange(-40, 101, 150).build()));
    weapons.add(new Weapon(15, "Composite bow", WeaponGroup.MISSILE, 4, new RangeBuilder().addRange(25, 1, 10).addRange(0, 11, 100).addRange(-35, 101, 200).addRange(-60, 201, 300).build()));
    weapons.add(new Weapon(16, "Heavy crossbow", WeaponGroup.MISSILE, 5, new RangeBuilder().addRange(30, 1, 20).addRange(0, 21, 100).addRange(-25, 101, 200).addRange(-40, 201, 300).addRange(-55, 301, 360).build()));
    weapons.add(new Weapon(17, "Light crossbow", WeaponGroup.MISSILE, 5, new RangeBuilder().addRange(15, 1, 10).addRange(0, 11, 100).addRange(-35, 101, 200).addRange(-50, 201, 300).addRange(-75, 301, 360).build()));
    weapons.add(new Weapon(18, "Long bow", WeaponGroup.MISSILE, 5, new RangeBuilder().addRange(20, 1, 10).addRange(0, 11, 100).addRange(-30, 101, 200).addRange(-40, 201, 300).addRange(-50, 301, 400).build()));
    weapons.add(new Weapon(19, "Short bow", WeaponGroup.MISSILE, 4, new RangeBuilder().addRange(10, 1, 10).addRange(0, 11, 100).addRange(-40, 101, 180).addRange(-70, 181, 240).build()));
    weapons.add(new Weapon(20, "Sling", WeaponGroup.MISSILE, 6, new RangeBuilder().addRange(15, 1, 10).addRange(0, 11, 60).addRange(-40, 61, 120).addRange(-65, 121, 180).build()));
    weapons.add(new Weapon(21, "Battle axe", WeaponGroup.TWO_HANDED, 5));
    weapons.add(new Weapon(22, "Flail", WeaponGroup.TWO_HANDED, 8));
    weapons.add(new Weapon(23, "War mattock", WeaponGroup.TWO_HANDED, 6));
    weapons.add(new Weapon(24, "Quarterstaff", WeaponGroup.TWO_HANDED, 3));
    weapons.add(new Weapon(25, "Two-handed sword", WeaponGroup.TWO_HANDED, 5));
    weapons.add(new Weapon(26, "Javelin", WeaponGroup.POLE_ARM, 4));
    weapons.add(new Weapon(27, "Lance", WeaponGroup.POLE_ARM, 7));
    weapons.add(new Weapon(28, "Pole arm", WeaponGroup.POLE_ARM, 7));
    weapons.add(new Weapon(29, "Spear", WeaponGroup.POLE_ARM, 5, new RangeBuilder().addRange(-10, 1, 10).addRange(-20, 11, 25).addRange(-30, 26, 50).build()));
  }
}
