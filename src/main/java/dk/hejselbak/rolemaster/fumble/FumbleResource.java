package dk.hejselbak.rolemaster.fumble;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.servers.Server;



@Path("/fumble")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(info=
  @Info(
    title="Rolemaster fumble service",
    version="0.2",
    description="Service used to obtain information about fumbles in Rolemaster. This yelds information from the ArmsLaw rulebook.",
    contact=@Contact(name="Developer", email="michael@weber-hansen.dk")
  ),
  servers=@Server(url="http://localhost:9000", description="Development server")
  )
@Slf4j
public class FumbleResource {

  @Inject FumbleService service;

  public FumbleResource() {
    log.info("Starting Critical Resource Service ...");
  }

  @GET
  public List<FumbleTable> list() {
    return service.listTables();
  }

  @GET
  @Path("/{id}")
  @Operation(summary = "Locate a specific fumble Table", description = "Returns the fumble Table, with the given id.")
  @APIResponse(responseCode = "200", description = "The table", content = @Content(schema = @Schema(implementation = FumbleTable.class)))
  @APIResponse(responseCode = "404", description = "Table not found, with the given id.")
  public FumbleTable getTable(@Parameter(description="The id of the fumble table to list.", required=true) @PathParam("id") Long id) {
    FumbleTable result = service.getFumbleTable(id);
    
    if (result == null) throw new NotFoundException();
    
    return result;
  }

  @GET
  @Path("/{id}/fumble")
  @Operation(summary = "Locate a specific fumble Table hit", description = "Returns the fumble result, given the type and roll")
  @APIResponse(responseCode = "200", description = "The fumble entry", content = @Content(schema = @Schema(implementation = FumbleEntry.class)))
  @APIResponse(responseCode = "204", description = "No fumble entry located. Most likely due to a type.")
  public FumbleEntry hit(@Parameter(description="The id of the fumble table to use.", required=true) @PathParam("id") Long id, 
      @Parameter(description="The modified roll of the fumble.", required=true) @NotNull @QueryParam("roll") Integer roll,
      @Parameter(description="The type of the fumble (for instance 'HAND_ARMS_ONE_HANDED', 'SPEAR_AND_POLE_ARMS', ...).", required=true) @NotNull @QueryParam("type") String type) {
    if (roll == null || roll < 0) {
      throw new WebApplicationException(
        Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
          .entity("'roll' parameter must be above 0 (roll was '" + roll + "').")
          .build()
      );
    }

    boolean validSev = false;
    for (FumbleCategory fg : FumbleCategory.values()) {
      validSev = fg.name().equalsIgnoreCase(type);
      if (validSev) break;
    }
    if (!validSev) {
      throw new WebApplicationException(
        Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
          .entity("'type' parameter must be a valid fumble type. For instance 'HAND_ARMS_ONE_HANDED', 'SPEAR_AND_POLE_ARMS', ... .")
          .build()
      );
    }

    FumbleEntry fe = service.getFumble(id, type, roll);

    if (fe == null) {
      throw new WebApplicationException(
        Response.status(HttpURLConnection.HTTP_NO_CONTENT)
          .entity("No fumble entry found. Most likely due to a invalid type.")
          .build()
      );
    }

    return fe; 
  }

  @GET
  @Path("/categories")
  public FumbleCategory[] getCategories() {
    return FumbleCategory.values();
  }
}
