package com.pragmatest.e2e;

import io.restassured.RestAssured;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Tag("e2e")
public class UserApiTest {

    @Test
    public void testHerokuApi() {
        // Dynamically fetch the base URL (replace this with your actual dynamic URL
        // variable)
        String baseUrl = System.getenv("HEROKU_APP_URL"); // Pass as an environment variable in GitHub Actions

        // Set the base URI for RestAssured
        RestAssured.baseURI = baseUrl;

        // add a comment
        // Perform a GET request and assert the response
        given()
                .header("accept", "*/*") // Optional, as it defaults to application/json
                .when()
                .get("/users")
                .then()
                .statusCode(200) // Check if status code is 200
                .contentType("application/json") // Check if response is JSON
                .body("$.size()", greaterThanOrEqualTo(1)) // Verify there's at least 1 user in the response
                .body("[0].fullName", notNullValue()) // Verify the first user's fullName is not null
                .body("[0].locality", notNullValue()) // Verify the first user's locality is not null
                .body("[0].age", greaterThan(0)); // Verify the first user's age is greater than 0

    }
}
