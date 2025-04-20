package ch.carve.homemagic;

import ch.carve.homemagic.model.Temperature;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.RunOnVirtualThread;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
    @RunOnVirtualThread
    public TemplateInstance get() {
        List<Temperature> temps1 = temperatureService.getLastHours(24L, 4L, "temperature1");
        List<Temperature> temps2 = temperatureService.getLastHours(24L, 4L, "temperature2");
        return temperature
                .data("temperature1", temps1)
                .data("temperature2", temps2)
                .data("active", "Temperature");
    }

}