package com.learnup.tests;

import com.learnup.dto.Category;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.learnup.Endpoints.CATEGORY_ENDPOINT;
import static com.learnup.enums.CategoryType.FURNITURE;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryTests extends BaseTest {

    @Test
    void getCategoryTest() {
        given()
                .response()
                .spec(categoriesResponseSpec)
                .when()
                .get(CATEGORY_ENDPOINT, 1);
    }

    @Test
    void getCategoryWithLogsTest() {
        given()
                .response()
                .spec(categoriesResponseSpec)
                .
        when()
                .get(CATEGORY_ENDPOINT, 1)
                .prettyPeek();
    }

    @Test
    void getCategoryWithAssertsTest() {
        given()
                .response()
                .spec(categoriesResponseSpec)
                .
        when()
                .get(CATEGORY_ENDPOINT, 1)
                .prettyPeek()
                .then()
                .body("id", equalTo(1));
    }

    @Test
    void getCategoryWithAssertsForResponseTest() {
        Response response =
                given()
                        .response()
                        .spec(categoriesResponseSpec)
                        .  when()
                        .get(CATEGORY_ENDPOINT, 1);
        Category responseBody = response.body().as(Category.class);
        assertThat(response.body().jsonPath().get("products[0].categoryTitle"), equalTo("Food"));
        assertThat(responseBody.getProducts().get(0).getCategoryTitle(), equalTo("Food"));
    }

    @Test
    void getCategoryWithAssertsAfterTestForResponseTest() {
        Category response =
                given()
                        .response()
                        .spec(categoriesResponseSpec)
                        . when()
                        .get(CATEGORY_ENDPOINT, FURNITURE.getId())
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
