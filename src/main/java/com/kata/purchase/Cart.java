package com.kata.purchase;

import com.kata.purchase.exception.ErrorCode;
import com.kata.purchase.exception.PurchaseException;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static com.kata.purchase.exception.ErrorCode.*;
import static java.lang.System.getProperty;
import static java.util.Comparator.comparing;

@Getter
@Setter
public class Cart {

    public static final String LINE_SEPARATOR = getProperty("line.separator");

    private Map<String, Item> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public void add(Item... items) throws PurchaseException {
        if (items == null) {
            throw new PurchaseException(ITEMS_SHOULD_NOT_NULL);
        }

        for (Item item : items) {
            addOneItem(item);
        }
    }

    private void addOneItem(Item item) throws PurchaseException {
        if (item == null || !item.isValid()) {
            throw new PurchaseException(ITEM_NOT_VALID);
        }
        String productId = item.getProduct().getId();
        if (productExistsAlready(productId)) {
            items.get(productId).increment();
        } else {
            items.put(productId, item);
        }
    }

    private boolean productExistsAlready(String productId) {
        return items.get(productId) != null;
    }

    public void removeItem(String productId) throws PurchaseException {
        findItem(productId);
        items.remove(productId);
    }

    public void updateProductQuantity(String productId, int quantity) throws PurchaseException {
        if (quantity < 0) {
            throw new PurchaseException(ErrorCode.QUANTITY_SHOULD_BE_GREATER_THAN_0);
        }
        Item item = findItem(productId);
        item.setQuantity(quantity);
    }

    private Item findItem(String productId) throws PurchaseException {
        Item item = items.get(productId);
        if (item == null) {
            throw new PurchaseException(ITEM_NOT_FOUND);
        }
        return item;
    }

    public void incrementQuantity(String productId) throws PurchaseException {
        Item item = findItem(productId);
        item.increment();
    }

    public void decrementQuantity(String productId) throws PurchaseException {
        Item item = findItem(productId);
        item.decrement();
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        items.values()
                .stream()
                .sorted(comparing(Item::getPrice).thenComparing(Item::getLabel).thenComparing(Item::getId))
                .forEach((item) -> sb.append(item.print()).append(LINE_SEPARATOR));
        return sb.toString();
    }

    public Double price() {
        return items.values()
                .stream()
                .map((item) -> item.getQuantity() * item.getPrice())
                .reduce(0D, (price1, price2) -> price1 + price2);
    }
}
