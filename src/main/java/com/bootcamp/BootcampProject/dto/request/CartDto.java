package com.bootcamp.BootcampProject.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartDto {
    @NotNull
    @Positive
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
