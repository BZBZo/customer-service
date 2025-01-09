package com.example.spring.bzcustomerservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDTO {
    private Long customerId;
    private Long productId;
    private Integer quantity;
}
