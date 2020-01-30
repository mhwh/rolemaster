package dk.hejselbak.weapon;

import java.net.HttpURLConnection;
import java.util.List;
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


@Path("/critical")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(info=
  @Info(
    title="Rolemaster critical service",
    version="0.2",
    description="Service used to obtain information about criticals in Rolemaster. This yelds information from the ArmsLaw rulebook.",
    contact=@Contact(name="Developer", email="michael@weber-hansen.dk")
  ),
  servers=@Server(url="http://localhost:8080", description="Development server")
  )
@Slf4j
public class CriticalResource {

  @Inject CriticalService service;

  public CriticalResource() {
    log.info("Starting Critical Resource Service ...");
  }

  @GET
  public List<CritTable> list() {
    return service.listTables();
  }

  @GET
  @Path("/{shortname}")
  @Operation(summary = "Locate a specific Critical Table", description = "Returns the Critical Table, with the given short name")
  @APIResponse(responseCode = "200", description = "The table", content = @Content(schema = @Schema(implementation = CritTable.class)))
  @APIResponse(responseCode = "404", description = "Table not found, with the given shortname")
  public CritTable getTable(@Parameter(description="The shortname of the critical table to list.", required=true) @PathParam("shortname") String shortName) {
    CritTable result = service.getCritTable(shortName);
    
    if (result == null) throw new NotFoundException();
    
    return result;
  }

  @GET
  @Path("/{shortname}/hit")
  @Operation(summary = "Locate a specific Critical Table hit", description = "Returns the Critical result, given the severity and roll")
  @APIResponse(responseCode = "200", description = "The critical entry", content = @Content(schema = @Schema(implementation = CriticalEntry.class)))
  @APIResponse(responseCode = "204", description = "No critical entry located. Most likely due to a invalid (larger than the table maximum) roll.")
  @APIResponse(responseCode = "404", description = "Entry not found, mort likely due to an invalid roll or invalid severity")
  public CriticalEntry hit(@Parameter(description="The shortname of the critical table to list.", required=true) @PathParam("shortname") String shortName, 
      @Parameter(description="The modified roll of the critical.", required=true) @NotNull @QueryParam("roll") Integer roll, 
      @Parameter(description="The severity of the critical (for instance 'A', 'B', ...).", required=true) @NotNull @QueryParam("severity") String sev) {
    if (roll == null || roll < 0) {
      throw new WebApplicationException(
        Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
          .entity("'roll' parameter must be above 0 (roll was '" + roll + "').")
          .build()
      );
    }

    boolean validSev = false;
    for (CritSeverity cs : CritSeverity.values()) {
      validSev = sev.equalsIgnoreCase(cs.name());
      if (validSev) break;
    }
    if (!validSev) {
      throw new WebApplicationException(
        Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
          .entity("'severity' parameter must be a valid critical severity. For instance 'A', or 'E'.")
          .build()
      );
    }

    CriticalEntry ce = service.getCritcal(shortName, sev.toUpperCase(), roll);

    if (ce == null) {
      throw new WebApplicationException(
        Response.status(HttpURLConnection.HTTP_NO_CONTENT)
          .entity("No critical entry found. Most likely due to a invalid (to large) roll.")
          .build()
      );
    }

    return ce; 
  }

  
}
