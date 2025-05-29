package controllers;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import java.util.Objects;

import static constants.Constants.BASE_URL;
import static constants.Constants.USER_ENDPOINT;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class UserController {
    RequestSpecification requestSpecification;

    public UserController() {
        this.requestSpecification = given()
        .baseUri(BASE_URL)
        .accept(JSON)
        .contentType(JSON)
        .filter(new AllureRestAssured());
    }

    @Step("Create user")
    public Response createUser(User user) {
        return given(this.requestSpecification)
                  .body(user)
                .when()
                  .post(USER_ENDPOINT)
                  .andReturn();
    }


     @Step("Update user")

    public Response updateUser(User user, String username) {
        return given(this.requestSpecification)
                .body(user)
                .when()
                .put(USER_ENDPOINT + "/" + username);
    }

    @Step("Get user by username")
    public Response getUser(String username) {
        return given(requestSpecification)
                .when()
                .get(USER_ENDPOINT  + "/" + username);
    }

    @Step("Get user and wait until he appears")
    public Response getUserByName(String username) {
        Response response = await()
                .atMost(15, SECONDS)
                .pollInterval(500, MILLISECONDS)
                .until(() -> {
                    Response resp = given(requestSpecification)
                            .get(USER_ENDPOINT + "/" + username)
                            .andReturn();
                    return resp.statusCode() == 200 ? resp : null;
                }, Objects::nonNull);

        if (response == null) {
            throw new RuntimeException("User '" + username + "' did not become available within timeout");
        }

        return response;
    }


    @Step("Delete user by username")
    public Response deleteUserByUsername(String username) {
        return given(this.requestSpecification)
                .when()
                .delete(USER_ENDPOINT + "/" + username)
                .andReturn();
    }
}
