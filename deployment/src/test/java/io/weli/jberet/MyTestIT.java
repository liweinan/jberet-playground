package io.weli.jberet;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTestIT {

    @Test
    public void testApi() {
        assertEquals("COMPLETED", RestAssured.given().baseUri("http://localhost/batch-deployment").basePath("/batch/start").port(8080)
                .get().asString());
    }


}
