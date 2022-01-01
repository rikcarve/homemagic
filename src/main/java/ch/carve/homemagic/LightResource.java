package ch.carve.homemagic;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Slf4j
@Path("/light")
@Produces(MediaType.TEXT_HTML)
public class LightResource {

    @Inject
    Template light;

    @Inject
    LightService lightService;

    @Inject
    @ConfigProperty(name = "world", defaultValue = "hello world")
    String world;

    @GET
    @Path("hello")
    public String hello() {
        return world + " from quarkus";
    }

    @GET
    @RolesAllowed("user")
    public TemplateInstance get() {
        List<Switch> data = lightService.getList();
        return light.data("light", data)
                .data("active", "Light");
    }

    @POST
    @RolesAllowed("user")
    @Path("/{id}/toggle/{status}")
    public Response toggle(@PathParam("id") String id, @PathParam("status") String status) {
        log.info("toggle {}, old status {}", id, status);
        lightService.toggle(id, "OFF".equals(status) ? "ON" : "OFF");
        return Response.status(301)
                .location(URI.create("/light"))
                .build();
    }
}