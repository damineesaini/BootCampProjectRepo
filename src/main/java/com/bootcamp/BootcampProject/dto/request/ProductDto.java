package com.bootcamp.BootcampProject.dto.request;

import java.util.List;

public class ProductDto {
    private String category;
    private List<ProductRequestParams> productRequestParams;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ProductRequestParams> getProductRequestParams() {
        return productRequestParams;
    }

    public void setProductRequestParams(List<ProductRequestParams> productRequestParams) {
        this.productRequestParams = productRequestParams;
    }
}
