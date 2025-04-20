package ch.carve.homemagic;

import ch.carve.homemagic.model.LightSwitch;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.RunOnVirtualThread;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;

@Slf4j
@Path("/light")
@Produces(MediaType.TEXT_HTML)
public class LightResource {

    @Inject
    Template light;

    @Inject
    LightService lightService;

    @GET
    @RolesAllowed("user")
    @RunOnVirtualThread
    public TemplateInstance get() {
        Collection<LightSwitch> data = lightService.getList();
        return light.data("light", data)
                .data("active", "Light");
    }

    @POST
    @RolesAllowed("user")
    @Path("/{id}/toggle/{status}")
    @RunOnVirtualThread
    public Response toggle(@PathParam("id") String id, @PathParam("status") String status) {
        log.info("toggle {}, old status {}", id, status);
        lightService.toggle(id, "OFF".equals(status) ? "ON" : "OFF");
        return Response.status(301)
                .location(URI.create("/light"))
                .build();
    }
}