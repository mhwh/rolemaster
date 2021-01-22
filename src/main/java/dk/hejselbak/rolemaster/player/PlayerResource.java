package dk.hejselbak.rolemaster.player;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(info=
@Info(
    title="Rolemaster Player resource",
    version="0.1",
    description="This endpoint can be used to obtain player information, as well as update the player information.",
    contact=@Contact(name="Developer", email="michael@weber-hansen.dk")
),
    servers=@Server(url="http://localhost:9000", description="Development server")
)
@Slf4j
public class PlayerResource {

    @Inject PlayerService service;

    public PlayerResource() {
        log.info("Starting the Player resource ...");
    }

    @GET
    public List<Player> getPlayers() {
        return service.getPlayers();
    }
}
