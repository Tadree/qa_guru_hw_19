package ru.jiehk.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RegistrationResponseSpec {
    public static ResponseSpecification successfulRegistrationResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectBody(matchesJsonSchemaInClasspath("schemas/successful-registration-schema.json"))
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification failRegistrationResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectBody(matchesJsonSchemaInClasspath("schemas/fail-registration-schema.json"))
            .expectStatusCode(400)
            .build();
}
