package com.kata.purchase.exception;


public enum ErrorCode {

    ITEMS_SHOULD_NOT_NULL("Items could not be null"),
    ITEM_NOT_FOUND("Item could not be found in the car"),
    ITEM_NOT_VALID("Item could not be added, Item is not valid!"),
    QUANTITY_SHOULD_BE_GREATER_THAN_0("Product quantity should be greater than 0");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
