package com.bootcamp.BootcampProject.dto.request;

import javax.validation.constraints.NotNull;

public class CartDto {
    @NotNull
    int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
