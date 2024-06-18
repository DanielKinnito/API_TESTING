import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginApiTest {

    @Test
    public void testLoginSuccess() {
        RestAssured.baseURI = "http://localhost:3000";

        given()
            .contentType(ContentType.JSON)
            .body("{ \"username\": \"user1\", \"password\": \"password1\" }")
        .when()
            .post("/login")
        .then()
            .statusCode(200)
            .body("message", equalTo("Login successful"));
    }

    @Test
    public void testLoginFailure() {
        RestAssured.baseURI = "http://localhost:3000";

        given()
            .contentType(ContentType.JSON)
            .body("{ \"username\": \"user1\", \"password\": \"wrongpassword\" }")
        .when()
            .post("/login")
        .then()
            .statusCode(401)
            .body("message", equalTo("Invalid credentials"));
    }
}
