package dk.hejselbak.rolemaster.weapon;

import java.net.HttpURLConnection;
import java.util.SortedSet;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;
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
  servers=@Server(url="http://localhost:9000", description="Development server")
  )
@Slf4j
public class WeaponResource {

  @Inject WeaponService service;
  private Integer maxRoll = 0;
  private Integer numberOfHits = 0;

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
  @Timed(name = "weaponTimes", description = "A measure of how long it takes to list a specific weapon", unit = MetricUnits.MILLISECONDS)
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
  @Counted(name = "attackCount", description = "The number of attacks that have been calculated")
  @Timed(name = "attackTimes", description = "A measure of how long it takes to calculate a attack", unit= MetricUnits.MILLISECONDS)
  public AttackResult hit(@Parameter(description = "The ID of the weapon to list.", required = true) @PathParam("id") int id,
      @Parameter(description = "The armorclass (1-20) of the target.", required = true) @NotNull @QueryParam("at") Integer at,
      @Parameter(description = "The modified roll of the hit. If this is below the fumblerange of the chosen weapon (id) then this method will return an error (400)", required = true) @NotNull @QueryParam("modifiedRoll") Integer roll)
      throws WebApplicationException {
    Weapon weapon = getWeapon(id);

    // Validate input parameters...
    if (at == null || at < 1 || at > 20) throw new WebApplicationException(
        Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
            .entity("'at' parameter is mandatory, and must be in the interval [1..20]")
            .build()
    );
    if (roll == null || roll <= 0)
      throw new WebApplicationException(
          Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
            .entity("'modifiedRoll' parameter is mandatory, and expected to be larger than 0.")
            .build()
        );


    // Collect metric on the highest roll and avg.
    if (roll > maxRoll) {
      maxRoll = roll;
    }
    numberOfHits++;

    return service.hit(weapon, at, roll);
  }

  @Gauge(name = "highestAttackLookup", unit = MetricUnits.NONE, description = "The highest attack roll so far.")
  public Integer highestAttackLookup() {
    return maxRoll;
  }

  @Gauge(name = "avgAttackLookup", unit = MetricUnits.NONE, description = "The average attack roll so far.")
  public Double avgAttackLookup() {
    return (numberOfHits == 0) ? 0d : maxRoll/numberOfHits;
  }

}
