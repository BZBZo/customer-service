package com.example.spring.bzcustomerservice.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQuantityDTO {
    private Long productId;
    private Integer quantity;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * JSON 문자열을 객체 리스트로 변환
     */
    public static List<ProductQuantityDTO> fromJson(String json) {
        try {
            return objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ProductQuantityDTO.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    /**
     * 객체 리스트를 JSON 문자열로 변환
     */
    public static String toJson(List<ProductQuantityDTO> products) {
        try {
            return objectMapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }
}
