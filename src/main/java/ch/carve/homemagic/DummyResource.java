package ch.carve.homemagic;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

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
