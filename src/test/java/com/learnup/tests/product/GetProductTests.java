package com.learnup.tests.product;

import com.learnup.db.model.Products;
import com.learnup.dto.Product;
import com.learnup.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static com.learnup.asserts.IsCategoryExists.isCategoryExists;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
@Epic("Tests for product")
@Story("Get Product tests")
public class GetProductTests extends BaseTest {
    @Severity(SeverityLevel.BLOCKER)
    @Test
    void getProductPositiveTest() {
        Product response = when()
                .get("http://80.78.248.82:8189/market/api/v1/products/18415")
                .prettyPeek()
                .body()
                .as(Product.class);
        Products products = productsMapper.selectByPrimaryKey(18415L);
//        assertThat(response.getCategoryTitle(), isCategoryExists());
        assertThat(response.getTitle(), CoreMatchers.equalTo(products.getTitle()));
        assertThat(response.getPrice(), CoreMatchers.equalTo(products.getPrice()));
    }
}
