package com.learnup.tests.product;

import com.github.javafaker.Faker;
import com.learnup.dto.Product;
import com.learnup.tests.BaseTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.learnup.Endpoints.POST_PRODUCT_ENDPOINT;
import static com.learnup.Endpoints.PRODUCT_ID_ENDPOINT;
import static com.learnup.enums.CategoryType.FURNITURE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostProductTests extends BaseTest {
    Faker faker = new Faker();
    Product product;
    Integer id;
    RequestSpecification postProductRequestSpec;
    ResponseSpecification postProductResponseSpec;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .price(100)
                .title(faker.food().dish())
                .categoryTitle(FURNITURE.getName())
                .build();

        postProductRequestSpec = new RequestSpecBuilder()
                .setBody(product)
                .setContentType(ContentType.JSON)
                .build();

        postProductResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectStatusLine("HTTP/1.1 201 ")
                .expectBody("title", equalTo(product.getTitle()))
                .expectBody("price", equalTo(product.getPrice()))
                .expectBody("categoryTitle", equalTo(product.getCategoryTitle()))
                .build();
    }

    @Test
    void postProductPositiveTest() {
        id = given(postProductRequestSpec, postProductResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void postProductWithDifferentAssertsPositiveTest() {
        Product response = given(postProductRequestSpec, postProductResponseSpec)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product.getTitle()));
        assertThat(response.getPrice(), equalTo(product.getPrice()));
    }

    @AfterEach
    void tearDown() {
        given()
                .response()
                .spec(deleteResponseSpec)
                .when()
                .delete(PRODUCT_ID_ENDPOINT, id)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
