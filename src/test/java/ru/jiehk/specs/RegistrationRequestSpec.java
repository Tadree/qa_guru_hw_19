package ru.jiehk.specs;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static ru.jiehk.helpers.CustomApiListener.withCustomTemplates;

public class RegistrationRequestSpec {
    public static RequestSpecification registrationRequestSpec = with()
            .filter(withCustomTemplates())
            .basePath("/api")
            .log().all()
            .contentType(JSON);
}
