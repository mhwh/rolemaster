package dk.hejselbak.weapon;

import java.net.HttpURLConnection;
import java.util.SortedSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
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

  private final Logger log = LoggerFactory.getLogger(WeaponResource.class);
  
  @Inject
  private WeaponService service;

  public WeaponResource() {
    log.info("Starting Weapon Resource Service ...");
  }

  @GET
  public SortedSet<Weapon> list() {
    return service.getWeapons();
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
    Weapon result = service.getWeapon(id);
    
    if (result == null) throw new NotFoundException();
    
    return result;
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
    return service.hit(weapon, at, roll);
  }

  
}
