package com.learnup.asserts;

import com.learnup.dto.Product;
import com.learnup.enums.CategoryType;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class IsProductArray extends TypeSafeMatcher<List<Product>> {
    @Override
    protected boolean matchesSafely( List<Product> products) {
        try {
            products.get(0).getId();
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void describeTo(final Description description) {

    }
}
