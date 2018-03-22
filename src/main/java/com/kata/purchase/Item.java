package com.kata.purchase;

import lombok.Getter;
import lombok.Setter;

import static com.kata.purchase.Constants.MESSAGE_SEPARATOR;
import static com.kata.purchase.Constants.QUANTITY;

@Getter
@Setter
public class Item {

    private Product product;
    private int quantity;

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Item(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public void increment() {
        this.quantity++;
    }

    public void decrement() {
        if (this.quantity == 0) {
            return;
        }
        this.quantity--;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.product.print())
                .append(MESSAGE_SEPARATOR)
                .append(this.printQuantity());
        return sb.toString();
    }

    private String printQuantity() {
        StringBuilder sb = new StringBuilder();
        sb.append(QUANTITY)
                .append(this.getQuantity());
        return sb.toString();
    }

    public Double getPrice() {
        return product == null ? null : product.getPrice();
    }

    public String getLabel() {
        return product == null ? null : product.getLabel();
    }

    public String getId() {
        return product == null ? null : product.getId();
    }


    public boolean isValid() {
        return this.product != null && this.product.isValid();
    }

}
