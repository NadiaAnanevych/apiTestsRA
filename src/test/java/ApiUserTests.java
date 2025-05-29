import io.restassured.response.Response;
import controllers.UserController;
import models.ApiResponse;
import models.User;
import models.UserBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

public class ApiUserTests {

    UserController userController = new UserController();


    String body = """
            {
              "id": 0,
              "username": "string",
              "firstName": "string",
              "lastName": "string",
              "email": "string",
              "password": "string",
              "phone": "string",
              "userStatus": 0
            }""";


    @Test
    void createUserSimpleTest() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .baseUri(BASE_URL)
                .when()
                .body(body)
                .post(USER_ENDPOINT)
                .andReturn();
        int actualCode = response.getStatusCode();
        assertEquals(200, actualCode);
    }

    @Test
    void checkingResponseBodyTest() {
        given()
                .baseUri(BASE_URL)
                .body(body)
                .log().all()
                .when()
                .post(USER_ENDPOINT)
                .then()
                .statusCode(200)
                .body("code", Matchers.equalTo(200),
                        "type", Matchers.equalTo("unknown"),
                        "message", Matchers.greaterThan(""));
        //"message", notNullValue(String.class));
    }

    @Test
    void createUserControllerTest() {
        Response response = userController.createUser(DEFAULT_USER);
        assertEquals(200, response.getStatusCode());
        ApiResponse user = response.as(ApiResponse.class);
        assertEquals(200, user.getCode());
        assertEquals("unknown", user.getType());
        assertTrue(Long.parseLong(user.getMessage()) > 9223372036854757568L);
    }

    @Test
    void updateUserTest() {
        String username = "string2";
        String body = """
                {
                    "id": 1,
                    "username": "string2",
                    "firstName": "string2",
                    "lastName": "string2",
                    "email": "string2",
                    "password": "string2",
                    "phone": "string2",
                    "userStatus": 1
                }
                """;
        given().
                baseUri(BASE_URL).
                accept("application/json").
                contentType("application/json").
                body(body).
                log().all().
                when().
                put("/v2/user/" + username).
                then().
                statusCode(200).
                log().all().
                body("code", Matchers.equalTo(200),
                        "type",  Matchers.equalTo("unknown"),
                        "message", Matchers.equalTo("1"));

}

    @Test
    void updateUserControllerTest() {
        String username = "string2";

        UserController userController = new UserController();

        Response response = userController.updateUser(UPDATED_USER, username);

        assertEquals(200, response.getStatusCode());
        ApiResponse user = response.as(ApiResponse.class);
        assertEquals(200, user.getCode());
        assertEquals("unknown", user.getType());
        assertEquals("1", user.getMessage());
    }
    @Test
    void getUserControllerWithWaitTest() {
        Response response = userController.getUserByName("string");


        assertEquals(200, response.getStatusCode());
        User user = response.as(User.class);
        assertTrue(user.getId() > 9223372036854755000L);
        assertEquals(expectedUser, user);
    }

    @Test
    void getUserControllerTest() {
        Response response = userController.getUser("string");


        assertEquals(200, response.getStatusCode());
        User user = response.as(User.class);
        assertTrue(user.getId() > 9223372036854758000L);
        assertEquals(expectedUser, user);
    }

    @Test
    void deleteUserControllerTest() {
        Response response = userController.deleteUserByUsername("string");
        
        assertEquals(200, response.getStatusCode());
        ApiResponse user = response.as(ApiResponse.class);
        assertEquals("unknown", user.getType());
        assertNotNull(user.getMessage());
    }
}
