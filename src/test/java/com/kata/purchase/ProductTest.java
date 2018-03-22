package com.kata.purchase;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProductTest {

    @Test
    public void given_product_having_null_id_when_isValid_then_return_false() {
        Product product = new Product.ProductBuilder(null)
                .label("LABEL")
                .available(false)
                .price(2.5)
                .build();

        boolean valid = product.isValid();

        assertThat(valid, is(false));
    }

    @Test
    public void given_product_having_null_price_when_isValid_then_return_false() {
        Product product = new Product.ProductBuilder("ID")
                .label("LABEL")
                .available(false)
                .price(null)
                .build();

        boolean valid = product.isValid();

        assertThat(valid, is(false));
    }

    @Test
    public void given_product_having_not_null_id_and_not_null_price_when_isValid_then_return_true() {
        Product product = new Product.ProductBuilder("ID")
                .price(2.5)
                .label("LABEL")
                .available(false)
                .build();

        boolean valid = product.isValid();

        assertThat(valid, is(true));
    }

    @Test
    public void given_product_when_print_then_should_print_all_details() {
        Product product = new Product.ProductBuilder("ID")
                .price(2.5)
                .label("LABEL")
                .available(false)
                .build();

        String message = product.print();

        assertThat(message, is("LABEL -- 2.5 EUR -- Not Available"));
    }

}
