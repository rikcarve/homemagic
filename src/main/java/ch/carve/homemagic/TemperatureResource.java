package ch.carve.homemagic;

import ch.carve.homemagic.model.Temperature;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Slf4j
@Path("/temperature")
@Produces(MediaType.TEXT_HTML)
public class TemperatureResource {

    @Inject
    Template temperature;

    @Inject
    TemperatureService temperatureService;

    @GET
    @RolesAllowed("user")
    public TemplateInstance get() {
        List<Temperature> temps = temperatureService.getLastHours(6L);

        return temperature.data("temperature", temps)
                .data("active", "Temperature");
    }

}