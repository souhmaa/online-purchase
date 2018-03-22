package com.kata.purchase;

import com.kata.purchase.exception.ErrorCode;
import com.kata.purchase.exception.PurchaseException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.kata.purchase.Cart.LINE_SEPARATOR;
import static com.kata.purchase.exception.ErrorCode.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CartTest {

    private Cart cart;

    @Before
    public void setUp() {
        this.cart = new Cart();
    }

    @Test
    public void given_null_item_when_add_then_throw_purchase_exception() {
        try {
            cart.add(null);
        } catch (PurchaseException e) {
            assertThat(e.getCode(), is(ITEMS_SHOULD_NOT_NULL));
        }
    }

    @Test
    public void given_product_item_having_null_id_when_add_then_throw_purchase_exception() {
        Product product = new Product.ProductBuilder(null)
                .available(false)
                .label("LABEL")
                .price(2.5)
                .build();
        Item item = new Item(product);

        try {
            cart.add(item);
        } catch (PurchaseException e) {
            assertThat(e.getCode(), is(ITEM_NOT_VALID));
        }
    }

    @Test
    public void given_item_has_1_product_when_add_then_should_find_the_inserted_item() throws PurchaseException {
        String productId = "ID";
        Product product = new Product.ProductBuilder(productId)
                .available(false)
                .label("LABEL")
                .price(2.5)
                .build();
        Item item = new Item(product);

        cart.add(item);

        Map<String, Item> items = cart.getItems();
        assertThat(items.size(), is(1));
        assertThat(items.get(productId).getQuantity(), is(1));
        assertThat(items.get(productId).getProduct().getId(), is(productId));
        assertThat(items.get(productId).getProduct().getPrice(), is(2.5));
        assertThat(items.get(productId).getProduct().getLabel(), is("LABEL"));
    }

    @Test
    public void given_item1_item2_and_item3_has_same_product_id_when_add_item1_item2_item3_then_cart_should_have_1_item_with_quantity_3_and_the_inserted_product() throws PurchaseException {
        String productId = "ID";
        Product product = new Product.ProductBuilder(productId)
                .available(false)
                .label("LABEL")
                .price(2.5)
                .build();
        Item item1 = new Item(product);
        Item item2 = new Item(product);
        Item item3 = new Item(product);

        cart.add(item1, item2, item3);

        Map<String, Item> items = cart.getItems();
        assertThat(items.size(), is(1));
        assertThat(items.get(productId).getQuantity(), is(3));
        assertThat(items.get(productId).getProduct().getId(), is(productId));
        assertThat(items.get(productId).getProduct().getPrice(), is(2.5));
        assertThat(items.get(productId).getProduct().getLabel(), is("LABEL"));
    }

    @Test
    public void given_item1_and_item2_with_different_products_when_add_item1_and_item2_then_cart_should_have_2_items_each_one_has_1_product() throws PurchaseException {
        String product1Id = "ID1";
        Product product1 = new Product.ProductBuilder(product1Id)
                .available(false)
                .label("LABEL1")
                .price(1.5)
                .build();
        String product2Id = "ID2";
        Item item1 = new Item(product1);
        Product product2 = new Product.ProductBuilder(product2Id)
                .available(true)
                .label("LABEL2")
                .price(2.5)
                .build();
        Item item2 = new Item(product2);

        cart.add(item1, item2);

        Map<String, Item> items = cart.getItems();
        assertThat(items.size(), is(2));
        assertThat(items.get(product1Id).getQuantity(), is(1));
        assertThat(items.get(product1Id).getProduct().getId(), is(product1Id));
        assertThat(items.get(product1Id).getProduct().getLabel(), is("LABEL1"));
        assertThat(items.get(product1Id).getProduct().isAvailable(), is(false));
        assertThat(items.get(product1Id).getProduct().getPrice(), is(1.5));
        assertThat(items.get(product2Id).getQuantity(), is(1));
        assertThat(items.get(product2Id).getProduct().getId(), is(product2Id));
        assertThat(items.get(product2Id).getProduct().getLabel(), is("LABEL2"));
        assertThat(items.get(product2Id).getProduct().isAvailable(), is(true));
        assertThat(items.get(product2Id).getProduct().getPrice(), is(2.5));
    }

    @Test
    public void given_empty_cart_when_remove_product_then_throw_exception() {
        try {
            cart.removeItem("productId");
        } catch (PurchaseException e) {
            assertThat(e.getCode(), is(ITEM_NOT_FOUND));
        }
    }

    @Test
    public void given_cart_has_item1_and_item2_each_one_has_one_product_when_remove_item1_then_cart_should_have_only_item2() throws PurchaseException {
        String productId1 = "productId1";
        Product product1 = new Product.ProductBuilder(productId1)
                .available(false)
                .label("LABEL1")
                .price(1.5)
                .build();
        Item item1 = new Item(product1);
        String productId2 = "productId2";
        Product product2 = new Product.ProductBuilder(productId2)
                .available(false)
                .label("LABEL2")
                .price(2.5)
                .build();
        Item item2 = new Item(product2);
        Map<String, Item> items = new HashMap<>();
        items.put(productId1, item1);
        items.put(productId2, item2);
        cart.setItems(items);

        cart.removeItem(productId1);

        items = cart.getItems();
        assertThat(items.size(), is(1));
        assertTrue(items.get(productId1) == null);
        assertThat(items.get(productId2).getQuantity(), is(1));
        assertThat(items.get(productId2).getProduct().getId(), is(productId2));
        assertThat(items.get(productId2).getProduct().getLabel(), is("LABEL2"));
        assertThat(items.get(productId2).getProduct().getPrice(), is(2.5));
    }

    @Test
    public void given_empty_cart_when_increment_quantity_then_throw_purchase_exception() {
        try {
            cart.incrementQuantity("productId");
        } catch (PurchaseException e) {
            assertThat(e.getCode(), is(ITEM_NOT_FOUND));
        }
    }

    @Test
    public void given_cart_has_item_with_1_product_when_increment_quantity_then_item1_has_2_products() throws PurchaseException {
        Product product1 = new Product.ProductBuilder("ID1")
                .label("LABEL1")
                .available(false)
                .price(2.5)
                .build();
        Item item1 = new Item(product1);
        cart.add(item1);

        cart.incrementQuantity("ID1");

        assertThat(cart.getItems().get("ID1").getQuantity(), is(2));
    }

    @Test
    public void given_empty_cart_when_decrement_quantity_then_throw_purchase_exception() {
        try {
            cart.decrementQuantity("ID1");
        } catch (PurchaseException e) {
            assertThat(e.getCode(), is(ITEM_NOT_FOUND));
        }
    }

    @Test
    public void given_cart_has_item_with_11_product_when_decrement_quantity_then_item1_has_10_products() throws PurchaseException {
        Product product1 = new Product.ProductBuilder("ID1")
                .label("LABEL1")
                .available(false)
                .price(2.5)
                .build();
        Item item1 = new Item(product1, 11);
        cart.add(item1);

        cart.decrementQuantity("ID1");

        assertThat(cart.getItems().get("ID1").getQuantity(), is(10));
    }

    @Test
    public void given_empty_cart_when_updateProductQuantity_then_throw_purchase_exception() {
        try {
            cart.updateProductQuantity("ID1", 10);
        } catch (PurchaseException e) {
            assertThat(e.getCode(), is(ITEM_NOT_FOUND));
        }
    }

    @Test
    public void given_cart_having_item_with_quantity_19_when_updateProductQuantity_user_negative_quantity_then_throw_purchase_exception() {
        try {
            cart.updateProductQuantity("ID1", -4);
        } catch (PurchaseException e) {
            assertThat(e.getCode(), is(ErrorCode.QUANTITY_SHOULD_BE_GREATER_THAN_0));
        }
    }

    @Test
    public void given_cart_has_item_with_10_products_when_updateProductQuantity_using_parameter_4_then_quantity_is_set_to_4() throws PurchaseException {
        Product product1 = new Product.ProductBuilder("ID1")
                .label("LABEL1")
                .available(false)
                .price(2.5)
                .build();
        Item item1 = new Item(product1, 10);
        cart.add(item1);

        cart.updateProductQuantity("ID1", 4);

        assertThat(cart.getItems().get("ID1").getQuantity(), is(4));
    }

    @Test
    public void given_empty_cart_when_consult_then_return_empty_string() {
        String message = cart.print();

        assertThat(message, is(""));
    }

    @Test
    public void given_cart_contains_3_items_and_each_item_contain_1_product_when_consult_then_should_get_all_products() throws PurchaseException {
        Product product1 = new Product.ProductBuilder("ID1")
                .label("LABEL1")
                .available(false)
                .price(2.5)
                .build();
        Item item1 = new Item(product1);
        Product product2 = new Product.ProductBuilder("ID2")
                .label("LABEL2")
                .available(false)
                .price(2.5)
                .build();
        Item item2 = new Item(product2);
        Product product3 = new Product.ProductBuilder("ID3")
                .label("LABEL3")
                .available(false)
                .price(4.5)
                .build();
        Item item3 = new Item(product3);
        cart.add(item1, item2, item3);

        String message = cart.print();

        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage
                .append("LABEL1 -- 2.5 EUR -- Not Available -- Quantity: 1")
                .append(LINE_SEPARATOR)
                .append("LABEL2 -- 2.5 EUR -- Not Available -- Quantity: 1")
                .append(LINE_SEPARATOR)
                .append("LABEL3 -- 4.5 EUR -- Not Available -- Quantity: 1")
                .append(LINE_SEPARATOR);
        assertThat(message, is(expectedMessage.toString()));
    }

    @Test
    public void given_empty_cart_when_price_then_return_0() {
        Double price = cart.price();

        assertThat(price, is(0D));
    }

    @Test
    public void given_cart_has_item1_having_2_products_with_price_10_and_item2_having_3_products_with_price_25_when_price_then_return_95() throws PurchaseException {
        Product product1 = new Product.ProductBuilder("ID1")
                .label("LABEL1")
                .available(false)
                .price(10D)
                .build();
        Item item1 = new Item(product1, 2);

        Product product2 = new Product.ProductBuilder("ID2")
                .label("LABEL2")
                .available(false)
                .price(25D)
                .build();
        Item item2 = new Item(product2, 3);
        cart.add(item1, item2);

        Double price = cart.price();

        assertThat(price, is(95D));
    }

}
