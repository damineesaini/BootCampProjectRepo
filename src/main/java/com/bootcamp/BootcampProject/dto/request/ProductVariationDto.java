package com.bootcamp.BootcampProject.dto.request;

import java.util.Map;

public class ProductVariationDto {
    private int quantityAvailable;
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

    public void setMetadataFieldValuesMap(Map<String, String> metadataFieldValuesMap) {
        this.metadataFieldValuesMap = metadataFieldValuesMap;
    }
}
