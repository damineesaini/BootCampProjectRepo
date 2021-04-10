package com.bootcamp.BootcampProject.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String, String>, String> {

        private static ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(Map<String, String> customerInfo) {

            String metadataJson = null;
            try {
                metadataJson = objectMapper.writeValueAsString(customerInfo);
            } catch (final JsonProcessingException e) {
                System.out.println(e);
            }

            return metadataJson;
        }

    @Override
        public Map<String, String> convertToEntityAttribute(String customerInfoJSON) {

            Map<String, String> metadata = null;
            try {
                metadata = objectMapper.readValue(customerInfoJSON, Map.class);
            } catch (final IOException e) {
                System.out.println(e);
            }

            return metadata;
        }
}

