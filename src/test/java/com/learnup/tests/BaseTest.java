package com.learnup.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.HEADERS;
import static io.restassured.filter.log.LogDetail.METHOD;
import static io.restassured.filter.log.LogDetail.URI;
import static org.hamcrest.Matchers.lessThan;

public abstract class BaseTest {





    static Properties properties = new Properties();
    static RequestSpecification logRequestSpecification;
    static ResponseSpecification responseSpecification;
    public static ResponseSpecification deleteResponseSpec;
    static ResponseSpecification categoriesResponseSpec;
    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");

        logRequestSpecification = new RequestSpecBuilder()
                .log(METHOD)
                .log(URI)
                .log(BODY)
                .log(HEADERS)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(900L), TimeUnit.MILLISECONDS)
                .build();

        categoriesResponseSpec =  new ResponseSpecBuilder()
                                .expectContentType(ContentType.JSON)
                                .expectStatusCode(200)
                                .expectStatusLine("HTTP/1.1 200")
                .build();
        deleteResponseSpec = new ResponseSpecBuilder()
                .expectContentType("")
                .build();
        RestAssured.requestSpecification = logRequestSpecification;
        RestAssured.responseSpecification = responseSpecification;
    }
}
