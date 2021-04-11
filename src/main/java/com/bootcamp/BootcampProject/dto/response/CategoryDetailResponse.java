package com.bootcamp.BootcampProject.dto.response;

import java.util.List;
import java.util.Set;

public class CategoryDetailResponse {
    private List<MetadataValueResponse> metadata;
    private Set<Object> brand;
    private Set<Object> price;

    public List<MetadataValueResponse> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataValueResponse> metadata) {
        this.metadata = metadata;
    }

    public Set<Object> getBrand() {
        return brand;
    }

    public void setBrand(Set<Object> brand) {
        this.brand = brand;
    }

    public Set<Object> getPrice() {
        return price;
    }

    public void setPrice(Set<Object> price) {
        this.price = price;
    }

}
