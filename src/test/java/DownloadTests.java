import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DownloadTests {

    @Test
    void testDownloadHttpClient() {
        String endpoint = "https://alfabank.servicecdn.ru/site-upload/67/dd/356/zayavlenie-IZK.pdf";
        String fileName = "downloaded.pdf";

        Response response =
                given().
                        when().
                        get(endpoint).
                        then().
                        contentType("application/pdf").
                        statusCode(200).
                        extract().response();
        savePdf(response, fileName);

        File downloadedFile = new File(fileName);
        assertThat(downloadedFile).exists();
    }


    public void savePdf(Response response, String fileName) {
        if (response != null) {
            // getting answer body in InputStream format
            try (InputStream inputStream = response.getBody().asInputStream()) {
                // creating file for saving PDF
                OutputStream outputStream = new FileOutputStream(fileName);

                // copy InputStream body into file
                int bytesRead;
                byte[] buffer = new byte[4096];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.close();

                System.out.println("PDF successfully saved: " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
