package ru.jiehk.tests;

import org.junit.jupiter.api.Test;
import ru.jiehk.models.RegistrationBody;
import ru.jiehk.models.RegistrationErrorResponse;
import ru.jiehk.models.RegistrationSuccessfulResponse;
import ru.jiehk.models.UsersListResponse;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.jiehk.specs.RegistrationRequestSpec.registrationRequestSpec;
import static ru.jiehk.specs.RegistrationResponseSpec.*;
import static ru.jiehk.specs.UserListResponseSpec.userListResponseSpec;
import static ru.jiehk.specs.UsersListRequestSpec.userListRequestSpec;

public class ReqresInTests extends TestBase {

    @Test
    void registrationTest() {
        RegistrationBody body = new RegistrationBody();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");
        RegistrationSuccessfulResponse response = step("Выполнение запроса на регистрацию", () ->
                given()
                        .spec(registrationRequestSpec)
                        .body(body)
                        .when()
                        .post("/register")
                        .then()
                        .spec(successfulRegistrationResponseSpec)
                        .extract().as(RegistrationSuccessfulResponse.class));

        step("Проверка ответа", () -> {
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
            assertEquals(4, response.getId());
        });
    }

    @Test
    void missingPasswordRegistrationTest() {
        RegistrationBody body = new RegistrationBody();
        body.setEmail("eve.holt@reqres.in");
        RegistrationErrorResponse response = step("Выполнение запроса на регистрацию", () ->
                given()
                        .spec(registrationRequestSpec)
                        .body(body)
                        .when()
                        .post("/register")
                        .then()
                        .spec(failRegistrationResponseSpec)
                        .extract().as(RegistrationErrorResponse.class));

        step("Проверка ответа", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    void missingEmailRegistrationTest() {
        RegistrationBody body = new RegistrationBody();
        body.setPassword("12345");
        RegistrationErrorResponse response = step("Выполнение запроса на регистрацию", () ->
                given()
                        .spec(registrationRequestSpec)
                        .body(body)
                        .when()
                        .post("/register")
                        .then()
                        .spec(failRegistrationResponseSpec)
                        .extract().as(RegistrationErrorResponse.class));

        step("Проверка ответа", () ->
                assertEquals("Missing email or username", response.getError()));
    }

    @Test
    void usersListPaginationTest() {
        UsersListResponse response = step("Выполнение запроса на получение списка пользователей", () ->
                given()
                        .spec(userListRequestSpec)
                        .param("page", 1)
                        .when()
                        .get("/users")
                        .then()
                        .spec(userListResponseSpec)
                        .extract().as(UsersListResponse.class));

        step("Проверка ответа", () ->
                assertEquals(response.getPer_page(), response.getData().size()));
    }

    @Test
    void emptyUsersListTest() {
        UsersListResponse response = step("Выполнение запроса на получение списка пользователей", () ->
                given()
                        .spec(userListRequestSpec)
                        .param("page", 100)
                        .when()
                        .get("/users")
                        .then()
                        .spec(userListResponseSpec)
                        .extract().as(UsersListResponse.class));

        step("Проверка ответа", () ->
                assertThat(response.getData()).isEmpty());
    }

}
