package ch.carve.homemagic;

import ch.carve.homemagic.model.LightSwitch;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Slf4j
@Path("/dimmable")
@Produces(MediaType.TEXT_HTML)
public class DimmableResource {

    @Inject
    Template dimmable;
    
    @Inject
    LightService lightService;

    @GET
    @RolesAllowed("user")
    @Path("/{id}")
    public TemplateInstance get(@PathParam("id") String id) {
        LightSwitch data = lightService.get(id);
        return dimmable.data("item", data)
                .data("active", "Light");
    }

    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/{id}/set")
    public Response toggle(@PathParam("id") String id, @FormParam("brightness") int brightness) {
        log.info("set brightness {} for {}",  brightness, id);
        LightSwitch lightSwitch = lightService.get(id);
        lightSwitch.setBrightness(brightness);
        String msg = lightSwitch.getMessageCreator().createDimmingMessage(lightSwitch);
        UdpSender.sendMessage(msg, lightSwitch.getIp(), lightSwitch.getPort());
        return Response.status(301)
                .location(URI.create("/dimmable/" + id))
                .build();
    }
}