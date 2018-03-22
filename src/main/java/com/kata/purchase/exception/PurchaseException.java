package com.kata.purchase.exception;

public class PurchaseException extends Exception {

    private ErrorCode code;

    public PurchaseException(ErrorCode errorCode) {
        this.code = errorCode;
    }

    @Override
    public String getMessage() {
        return code.getMessage();
    }

    public ErrorCode getCode() {
        return code;
    }

}
