package dk.hejselbak.rolemaster.weapon;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/weapons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(info=
  @Info(
    title="Rolemaster Weapon service",
    version="0.2",
    description="Service used to obtain information about weapontypes in Rolemaster. This yelds information from the ArmsLaw rulebook.",
    contact=@Contact(name="Developer", email="michael@weber-hansen.dk")
  ),
  servers=@Server(url="http://localhost:8080", description="Development server")
  )
@Slf4j
public class WeaponResource {

  @Inject WeaponService service;

  public WeaponResource() {
    log.info("Starting Weapon Resource Service ...");
  }

  @GET
  public SortedSet<Weapon> list() {
    return service.getWeapons();
  }

  @GET
  @Path("/{id}")
  @Operation(summary = "Locate a specific weapon", description = "Returns a weapon, with the given id")
  @APIResponse(responseCode = "200", description = "The weapon", content = @Content(schema = @Schema(implementation = Weapon.class)))
  @APIResponse(responseCode = "404", description = "Weapon not found, with the given id")
  public Weapon getWeapon(@Parameter(description="The ID of the weapon to list.", required=true) @PathParam("id") int id) {
    Weapon result = service.getWeapon(id);
    
    if (result == null) throw new NotFoundException();
    
    return result;
  }

  @GET
  @Path("/{id}/hit")
  @Operation(summary = "Looks up the result of a hit with the specific weapon on the specific AT.",
    description = "Returns a attack result, that can be 0, a number of hits, or a number of hits and a crit (severity and type)")
  @APIResponse(responseCode = "200", description = "The attack result", content = @Content(schema = @Schema(implementation = AttackResult.class)))
  @APIResponse(responseCode = "400", description = "Invalid parameters, either 'at' or 'modifiedRoll' are not valid.")
  @APIResponse(responseCode = "404", description = "Weapon not found, with the given id")
  public AttackResult hit(@Parameter(description="The ID of the weapon to list.", required=true) @PathParam("id") int id, 
      @Parameter(description="The armorclass (1-20) of the target.", required=true) @NotNull @QueryParam("at") Integer at, 
      @Parameter(description="The modified roll of the hit. If this is below the fumblerange of the chosen weapon (id) then this method will return an error (400)", required=true) @NotNull @QueryParam("modifiedRoll") Integer roll) {
    Weapon weapon = getWeapon(id);

    // Valicate input parameters...
    if (at == null || at < 1 || at > 20) {
      throw new WebApplicationException(
        Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
          .entity("'at' parameter is mandatory, and must be in the interval [1..20]")
          .build()
      );
    }
    if (roll == null || roll <= weapon.getFumble()) {
      throw new WebApplicationException(
        Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
          .entity("'modifiedRoll' parameter is mandatory. It must be above the fumble of the chosen weapon (" + weapon.getName() + " fumble is " + weapon.getFumble() + ").")
          .build()
      );
    }
    
    return service.hit(weapon, at, roll);
  }

  
}