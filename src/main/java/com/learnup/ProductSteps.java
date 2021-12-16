package com.learnup;

import com.learnup.dto.Category;
import com.learnup.dto.Product;
import lombok.experimental.UtilityClass;

import static com.learnup.Endpoints.POST_PRODUCT_ENDPOINT;
import static io.restassured.RestAssured.given;

@UtilityClass
public class ProductSteps {
    public Product postProduct(){
      return   given()
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .body()
                .as(Product.class);
    }

    public Product postProduct(Product product){
        return   given()
                .body(product)
                .post(POST_PRODUCT_ENDPOINT)
                .prettyPeek()
                .body()
                .as(Product.class);
    }
}
