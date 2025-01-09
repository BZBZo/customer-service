package com.example.spring.bzcustomerservice.entity;

import com.example.spring.bzcustomerservice.dto.ProductQuantityDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Cart 테이블의 기본 키

    @Column(nullable = false)
    private Long customerId; // 고객 ID (memberNo)

    @Column(columnDefinition = "json")
    @Type(type = "json")
    private List<ProductQuantityDTO> products = new ArrayList<>(); // 상품 ID와 수량 저장
}

