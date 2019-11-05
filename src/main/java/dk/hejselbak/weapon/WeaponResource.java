package dk.hejselbak.weapon;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Arrays;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/weapons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WeaponResource {

  private Set<Weapon> weapons = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

  public WeaponResource() {
    weapons.add(new Weapon(0, "Dagger", WeaponGroup.ONE_HANDED_SLASHING, 1, new RangeBuilder().addRange(-10, 1, 10).addRange(-20, 11, 25).addRange(-30, 26, 50).build()));
    weapons.add(new Weapon(1, "Falchion", WeaponGroup.ONE_HANDED_SLASHING, 5));
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

  @GET
  public Set<Weapon> list() {
    return weapons;
  }

  @GET
  @Path("/{id}")
  public Weapon getWeapon(@PathParam("id") int id) {
    for (Weapon w : weapons) {
      if (w.getId() == id) {
        return w;
      }
    }
    return null;
  }


}
