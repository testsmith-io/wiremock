package io.testsmith;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockTest {

    WireMockServer wireMockServer;

    @BeforeClass
    public void setupMock() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
        wireMockServer.start();

        stubFor(get(urlEqualTo("/helloworld"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("\"hello\": \"world\"")));

    }

    @Test
    public void helloworldTest() {
        RestAssured.given()
                .when().get("helloworld")
                .then()
                .statusCode(200);
    }

    @AfterMethod
    public void stopMock() {
        wireMockServer.stop();
    }

}
