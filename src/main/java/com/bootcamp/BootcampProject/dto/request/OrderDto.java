package com.bootcamp.BootcampProject.dto.request;

import javax.validation.constraints.NotNull;

public class OrderDto {
    @NotNull
    private String label;
    @NotNull
    private String paymentMethod;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
