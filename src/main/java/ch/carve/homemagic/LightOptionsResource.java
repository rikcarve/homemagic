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

@Slf4j
@Path("/lightoptions")
@Produces(MediaType.TEXT_HTML)
public class LightOptionsResource {

    @Inject
    Template lightoptions;
    
    @Inject
    LightService lightService;

    @GET
    @RolesAllowed("user")
    @Path("/{id}")
    @RunOnVirtualThread
    public TemplateInstance get(@PathParam("id") String id) {
        LightSwitch data = lightService.get(id);
        return lightoptions.data("item", data)
                .data("active", "Light");
    }

    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/{id}/brightness")
    @RunOnVirtualThread
    public Response brightness(@PathParam("id") String id, @FormParam("brightness") int brightness) {
        log.info("set brightness {} for {}",  brightness, id);
        LightSwitch lightSwitch = lightService.get(id);
        lightSwitch.setBrightness(brightness);
        lightSwitch.switchBrightness();
        return Response.status(301)
                .location(URI.create("/lightoptions/" + id))
                .build();
    }

    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/{id}/temperature")
    @RunOnVirtualThread
    public Response temperature(@PathParam("id") String id, @FormParam("temperature") int temperature) {
        log.info("set temperature {} for {}",  temperature, id);
        LightSwitch lightSwitch = lightService.get(id);
        lightSwitch.setColorTemperature(temperature);
        lightSwitch.switchColorTemperature();
        return Response.status(301)
                .location(URI.create("/lightoptions/" + id))
                .build();
    }

    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/{id}/color")
    @RunOnVirtualThread
    public Response color(@PathParam("id") String id, @FormParam("color") int color) {
        log.info("set color {} for {}",  color, id);
        LightSwitch lightSwitch = lightService.get(id);
        lightSwitch.setColorHue(color);
        lightSwitch.switchColorHue();
        return Response.status(301)
                .location(URI.create("/lightoptions/" + id))
                .build();
    }

}