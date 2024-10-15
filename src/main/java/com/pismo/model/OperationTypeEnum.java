package com.pismo.model;

public enum OperationTypeEnum {
    PAYMENT(1, "Payment"),
    WITHDRAWAL(3, "Withdrawal");

    private final int id;
    private final String description;

    OperationTypeEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
