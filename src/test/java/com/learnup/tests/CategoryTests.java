package com.learnup.tests;

import com.learnup.dto.Category;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import static com.learnup.enums.CategoryType.FURNITURE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryTests {
    public static final String CATEGORY_ENDPOINT = "categories/{id}";
    static Properties properties = new Properties();

    @BeforeAll
    static void setUp() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");
        ;
    }

    @Test
    void getCategoryTest() {
        given()
                .when()
                .get(CATEGORY_ENDPOINT, 1)
                .then()
                .statusCode(200);
    }

    @Test
    void getCategoryWithLogsTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get("/categories/1")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getCategoryWithAssertsTest() {
        given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get("/categories/1")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    void getCategoryWithAssertsForResponseTest() {
        Response response = given()
                .when()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .when()
                .get("/categories/1")
                .prettyPeek();
        Category responseBody = response.body().as(Category.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().jsonPath().get("products[0].categoryTitle"), equalTo("Food"));
        assertThat(responseBody.getProducts().get(0).getCategoryTitle(), equalTo("Food"));
    }

    @Test
    void getCategoryWithAssertsAfterTestForResponseTest() {
        Category response = given()
                .log()
                .method()
                .log()
                .uri()
                .log()
                .body()
                .expect()
                .statusCode(200)
                .when()
                .get("/categories/{id}", FURNITURE.getId())
                .prettyPeek()
                .body()
                .as(Category.class);
        response.getProducts().forEach(
                e -> assertThat(e.getCategoryTitle(), equalTo(FURNITURE.getName()))
        );
                assertThat(response.getTitle(), equalTo(FURNITURE.getName()));
                assertThat(response.getId(), equalTo(FURNITURE.getId()));

    }
}
