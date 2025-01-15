package com.example.spring.bzcustomerservice.dto;

import com.fasterxml.jackson.core.type.TypeReference;
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

    public static List<ProductQuantityDTO> fromJson(String json) {
        if (json == null || json.isEmpty()) {
            return List.of(); // 빈 리스트 반환
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<ProductQuantityDTO>>() {});
        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 중 오류 발생: " + json, e);
        }
    }


    public static String toJson(List<ProductQuantityDTO> productList) {
        if (productList == null) {
            return "[]"; // 빈 JSON 배열 반환
        }
        try {
            return objectMapper.writeValueAsString(productList);
        } catch (Exception e) {
            throw new RuntimeException("JSON 변환 중 오류 발생: " + productList, e);
        }
    }

}
