package com.example.spring.bzcustomerservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQuantityDTO {
    private Long productId; // 상품 ID
    private Integer quantity; // 수량

    public ProductQuantityDTO(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // 기본 생성자 필요 (JPA 및 JSON 직렬화를 위해)
    public ProductQuantityDTO() {}
}
