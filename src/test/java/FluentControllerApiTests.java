import controllers.FluentUserController;
import models.User;
import models.UserBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static constants.Constants.DEFAULT_USER;

public class FluentControllerApiTests {
    FluentUserController fluentUserController = new FluentUserController();

    @BeforeEach
    @AfterEach
    void clear() {
        fluentUserController.deleteUserByUsername(DEFAULT_USER.getUsername());
    }

    @Test
    void addUserExtendedTest() throws InterruptedException {
        String expectedId = fluentUserController.createUser(DEFAULT_USER)
                .statusCodeIs(200)
                .getJsonValue("message");
        Thread.sleep(3000);

        fluentUserController.getUser(DEFAULT_USER.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("id", expectedId)
                .jsonValueIs("username", DEFAULT_USER.getUsername())
                .jsonValueIs("email", DEFAULT_USER.getEmail());
    }

    @Test
    void updateUserTest() throws InterruptedException {
        UserBuilder updatedUser = new UserBuilder().id(1).username("test").lastName("test22").lastName("test35").userStatus(2)
                .build();
        String createdUserId = fluentUserController.updateUser(updatedUser)
                .statusCodeIs(200)
                .getJsonValue("message");
        Thread.sleep(3000);

        fluentUserController.updateUser(updatedUser)
                .statusCodeIs(200);

        fluentUserController.getUser(updatedUser.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("id", createdUserId)
                .jsonValueIs("username", updatedUser.getUsername())
                .jsonValueIs("firstName", updatedUser.getFirstName())
                .jsonValueIs("lastName", updatedUser.getLastName())
                .jsonValueIs("email", updatedUser.getEmail())
                .jsonValueIs("password", updatedUser.getPassword())
                .jsonValueIs("phone", updatedUser.getPhone())
                .jsonValueIs("userStatus", String.valueOf(updatedUser.getUserStatus()));
    }

    @Test
    void getUserTest() throws InterruptedException {
        String createdUserId = fluentUserController.createUser(DEFAULT_USER).statusCodeIs(200).getJsonValue("message");
        Thread.sleep(3000);
        fluentUserController.getUser(DEFAULT_USER.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("id", createdUserId)
                .jsonValueIs("username", DEFAULT_USER.getUsername())
                .jsonValueIs("firstName", DEFAULT_USER.getFirstName())
                .jsonValueIs("lastName", DEFAULT_USER.getLastName())
                .jsonValueIs("email", DEFAULT_USER.getEmail())
                .jsonValueIs("password", DEFAULT_USER.getPassword())
                .jsonValueIs("phone", DEFAULT_USER.getPhone())
                .jsonValueIs("userStatus", String.valueOf(DEFAULT_USER.getUserStatus()));
    }

    @Test
    void deleteUserTest() {
        fluentUserController.createUser(DEFAULT_USER);
        fluentUserController.deleteUserByUsername(DEFAULT_USER.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("code", "200")
                .jsonValueIs("type", "unknown")
                .jsonValueIs("message", DEFAULT_USER.getUsername());
        //fluentUserController.getUser(DEFAULT_USER.getUsername()).statusCodeIs(404);
    }


    @Test
    void builderTest(){
        UserBuilder userBuilder = new UserBuilder().id(1).username("test").lastName("test22").lastName("test35").userStatus(2)
                .build();
        System.out.println(userBuilder.toString());
    }
}
