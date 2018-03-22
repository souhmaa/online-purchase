package com.kata.purchase;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ItemTest {

    @Test
    public void given_valid_product_when_instantiate_item_with_only_product_parameter_then_quantity_should_be_equals_to_1() {
        Product product = new Product.ProductBuilder("ID")
                .label("LABEL")
                .price(2.5)
                .available(false)
                .build();

        Item item = new Item(product);

        assertThat(item.getQuantity(), is(1));
    }

    @Test
    public void given_item_has_quantity_equals_to_100_when_increment_then_quantity_value_changes_to_101() {
        Product product = new Product.ProductBuilder("ID")
                .label("LABEL")
                .price(2.5)
                .available(false)
                .build();
        int quantity = 100;
        Item item = new Item(product, quantity);

        item.increment();

        assertThat(item.getQuantity(), is(101));
    }

    @Test
    public void given_item_has_quantity_equals_to_0_when_decrement_then_quantity_does_not_change() {
        Product product = new Product.ProductBuilder("ID")
                .label("LABEL")
                .price(2.5)
                .available(false)
                .build();
        int quantity = 0;
        Item item = new Item(product, quantity);

        item.decrement();

        assertThat(item.getQuantity(), is(0));
    }

    @Test
    public void given_item_has_quantity_equals_to_10_when_decrement_then_quantity_change_to_9() {
        Product product = new Product.ProductBuilder("ID")
                .label("LABEL")
                .price(2.5)
                .available(false)
                .build();
        int quantity = 10;
        Item item = new Item(product, quantity);

        item.decrement();

        assertThat(item.getQuantity(), is(9));
    }

    @Test
    public void given_item_with_null_product_when_isValid_then_return_false() {
        Item item = new Item(null);

        boolean valid = item.isValid();

        assertThat(valid, is(false));
    }


    @Test
    public void given_product_item_having_null_id_and_not_null_price_when_isValid_then_return_false() {
        Product product = new Product.ProductBuilder(null).label("LABEL")
                .price(2.5)
                .available(false)
                .build();
        Item item = new Item(product);

        boolean valid = item.isValid();

        assertThat(valid, is(false));

    }

    @Test
    public void given_product_item_having_null_price_and_not_null_id_when_isValid_then_return_false() {
        Product product = new Product.ProductBuilder("ID").label("LABEL")
                .price(null)
                .available(false)
                .build();
        Item item = new Item(product);

        boolean valid = item.isValid();

        assertThat(valid, is(false));
    }

    @Test
    public void given_product_item_having_not_null_id_and_not_null_price_when_isValid_then_return_true() {
        Product product = new Product.ProductBuilder("ID")
                .label("LABEL")
                .price(2.5)
                .available(false)
                .build();
        Item item = new Item(product);

        boolean valid = item.isValid();

        assertThat(valid, is(true));
    }

    @Test
    public void given_product_item_when_print_item_then_should_display_all_product_details_and_quantity_information() {
        Product product = new Product.ProductBuilder("ID")
                .label("LABEL")
                .price(2.5)
                .available(false)
                .build();
        Item item = new Item(product, 20);

        String message = item.print();

        assertThat(message, is("LABEL -- 2.5 EUR -- Not Available -- Quantity: 20"));
    }

}
