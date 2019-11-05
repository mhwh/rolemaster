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
    weapons.add(new Weapon(0, "Broardsword", WeaponGroup.ONE_HANDED_EDGED, 5, 10));
    weapons.add(new Weapon(1, "Rapier", WeaponGroup.ONE_HANDED_EDGED, 4, 20));
    weapons.add(new Weapon(2, "Falcion", WeaponGroup.ONE_HANDED_EDGED, 3, 20));
    weapons.add(new Weapon(3, "Two-Handed Sword", WeaponGroup.TWO_HANDED_EDGED, 8, 8));
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
