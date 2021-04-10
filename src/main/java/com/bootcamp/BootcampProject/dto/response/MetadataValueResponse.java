package com.bootcamp.BootcampProject.dto.response;

public class MetadataValueResponse {
    private MetadataFieldResponse metadataFieldResponse;
    private String values;

    public MetadataFieldResponse getMetadataFieldResponse() {
        return metadataFieldResponse;
    }

    public void setMetadataFieldResponse(MetadataFieldResponse metadataFieldResponse) {
        this.metadataFieldResponse = metadataFieldResponse;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
