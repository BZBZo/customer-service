package com.example.spring.bzcustomerservice.entity;

import com.example.spring.bzcustomerservice.dto.ProductQuantityDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자는 protected로 설정
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Cart 테이블의 기본 키

    @Column(nullable = false)
    private Long customerId; // 고객 ID (memberNo)

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String products; // JSON 문자열로 상품 ID와 수량 저장

    /**
     * 정적 메서드: 빈 장바구니 생성
     * @param customerId 고객 ID
     * @return 빈 Cart 객체
     */
    public static Cart createEmptyCart(Long customerId) {
        return Cart.builder()
                .customerId(customerId)
                .products("[]") // 초기값으로 빈 JSON 배열 설정
                .build();
    }

    /**
     * 상품 추가 메서드
     * @param productList JSON 문자열로 저장된 기존 상품 리스트
     * @param newProduct 추가할 상품 (ProductQuantityDTO 형태)
     * @return 업데이트된 상품 리스트(JSON 문자열)
     */
    public static String addProductToCart(String productList, ProductQuantityDTO newProduct) {
        // 기존 JSON 문자열을 List<ProductQuantityDTO>로 변환 후 새로운 상품 추가
        List<ProductQuantityDTO> currentProducts = ProductQuantityDTO.fromJson(productList);
        currentProducts.add(newProduct);

        // 업데이트된 리스트를 다시 JSON 문자열로 변환
        return ProductQuantityDTO.toJson(currentProducts);
    }
}
