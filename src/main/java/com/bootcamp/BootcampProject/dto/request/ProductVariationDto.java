package com.bootcamp.BootcampProject.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Map;

public class ProductVariationDto {
    @NotNull
    @Positive
    private int quantityAvailable;
    @NotNull
    @Positive
    private double price;
    private Map<String,String> metadataFieldValuesMap;

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Map<String, String> getMetadataFieldValuesMap() {
        return metadataFieldValuesMap;
    }

    public void setMetadataFieldValuesMap(Map<String,String> metadataFieldValuesMap) {
        this.metadataFieldValuesMap = metadataFieldValuesMap;
    }

}
