package ch.carve.homemagic;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusIntegrationTest
public class DummyIT {
    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/dummy")
                .then()
                .statusCode(200)
                .body(is("world"));
    }

}
