import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UploadTests {
    @Test
    void uploadFileTest() {
        String apiUrl = "https://petstore.swagger.io/v2/pet/33/uploadImage";
        File file = new File("src/main/resources/cat.jpg");
        Response response =
                given()
                        .header("accept", "application/json")
                        .contentType("multipart/form-data")
                        .multiPart("file", file, "image/jpeg")
                        .when()
                        .post(apiUrl)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        System.out.println("Response: " + response.asString());
    }


}
