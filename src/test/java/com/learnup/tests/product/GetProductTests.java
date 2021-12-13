package com.learnup.tests.product;

import com.learnup.dto.Product;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.learnup.asserts.IsCategoryExists.isCategoryExists;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
@Epic("Tests for product")
@Story("Get Product tests")
public class GetProductTests {
    @Severity(SeverityLevel.BLOCKER)
    @Test
    void getProductPositiveTest() {
        Product response = when()
                .get("http://80.78.248.82:8189/market/api/v1/products/18179")
                .prettyPeek()
                .body()
                .as(Product.class);

        assertThat(response.getCategoryTitle(), isCategoryExists());
    }
}
