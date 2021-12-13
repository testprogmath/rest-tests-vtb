package com.learnup.tests.product;

import com.learnup.dto.Product;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.learnup.asserts.IsCategoryExists.isCategoryExists;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetProductTests {
    @Test
    void getProductPositiveTest() {
        Product response = when()
                .get("http://80.78.248.82:8189/market/api/v1/products/15830")
                .prettyPeek()
                .body()
                .as(Product.class);

        assertThat(response.getCategoryTitle(), isCategoryExists());
    }
}
