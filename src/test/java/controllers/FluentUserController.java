package controllers;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import models.User;
import models.UserBuilder;

import static constants.Constants.BASE_URL;
import static constants.Constants.USER_ENDPOINT;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


public class FluentUserController {
    private final RequestSpecification requestSpecification;

    public FluentUserController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification = given()
        .baseUri(BASE_URL)
        .accept(JSON)
        .contentType(JSON)
        .filter(new AllureRestAssured());
    }

    @Step("Create user")
    public HttpResponse createUser(User user) {
        this.requestSpecification.body(user);
        return new HttpResponse(given(this.requestSpecification).post(USER_ENDPOINT).then());
    }


     @Step("Update user")

    public HttpResponse updateUser(UserBuilder user) {
         this.requestSpecification.body(user);
         return new HttpResponse(given(this.requestSpecification).put(USER_ENDPOINT + "/" + user.getUsername()).then());
    }

    @Step("Get user by username")
    public HttpResponse getUser(String username) {
        return new HttpResponse(given(this.requestSpecification).get(USER_ENDPOINT + "/" + username).then());
    }


    @Step("Delete user by username")
    public HttpResponse deleteUserByUsername(String username) {
        return new HttpResponse(given(this.requestSpecification).delete(USER_ENDPOINT + "/" + username).then());
    }

}
