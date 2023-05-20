package ch.carve.homemagic;

import lombok.extern.slf4j.Slf4j;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Slf4j
@Path("dummy")
public class DummyResource {
    @GET
    public Response delay() throws InterruptedException {
        log.info("get delay");
        Thread.sleep(200);
        return Response.ok("OK").build();
    }

}
