import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TesteCliente {

    String apiAdress = "https://reqres.in/api/";
    String usersEndpoint = "users";
    String allUsersEndpoint = "users?page=2";


    @Test
    @DisplayName("When asking for all clients, we should get all of them")
    public void getAllUsers(){

        given().
            contentType(ContentType.JSON)
        .when()
            .get(apiAdress + allUsersEndpoint)
        .then()
                .statusCode(200)
                .assertThat().body( containsString("total\":12"));

    }

    @Test
    @DisplayName("When creating an user then it should be available")
    public void createUser(){

        String createUser = "{\n" +
                                    "\"name\": \"Tide\",\n" +
                                    "\"age\": \"8\"\n" +
                                    "}";

        String expectedAnswer = "\"name\":\"Tide\"";

        given()
                .contentType(ContentType.JSON)
                .body(createUser)
        .when()
                .post(apiAdress+usersEndpoint)
        .then()
                .statusCode(201)
                .assertThat().body(containsString(expectedAnswer));

    }

    @Test
    @DisplayName("When updating a user, the data should change")
    public void updateUser() {

        String updateUser = "{\n" +
                "\"name\": \"Tide\",\n" +
                "\"age\": \"7\"\n" +
                "}";

        String expectedAnswer = "\"age\":\"7\"";

        given()
                .contentType(ContentType.JSON)
                .body(updateUser)
        .when()
                .put(apiAdress+usersEndpoint+"/2")
        .then()
                .statusCode(200)
                .assertThat().body(containsString(expectedAnswer));

    }

    @Test
    @DisplayName("When deleting a user, it should be empty")
    public void deletaCLiente() {

        String expectedAnswer = "";

        given().
                contentType(ContentType.JSON)
        .when()
                .delete(apiAdress+usersEndpoint+"/2")
        .then()
                .statusCode(204)
                .assertThat().body(new IsEqual<>(expectedAnswer));
    }

}
