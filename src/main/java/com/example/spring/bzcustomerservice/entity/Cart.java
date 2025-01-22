package com.example.spring.bzcustomerservice.entity;

import com.example.spring.bzcustomerservice.dto.ProductQuantityDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberNo;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String products;

    /**
     * 정적 메서드: 빈 장바구니 생성
     * @param memberNo 회원 번호
     * @return 빈 Cart 객체
     */
    public static Cart createEmptyCart(Long memberNo) {
        return Cart.builder()
                .memberNo(memberNo)
                .products(ProductQuantityDTO.toJson(new ArrayList<>())) // 빈 리스트 JSON 변환
                .build();
    }

    /**
     * 상품을 장바구니에 추가하는 메서드
     * @param newProduct 추가할 상품 정보
     */
    public void addProductToCart(ProductQuantityDTO newProduct) {
        List<ProductQuantityDTO> currentProducts = ProductQuantityDTO.fromJson(this.products);

        // 동일한 상품이 있는 경우 수량 증가
        boolean productExists = false;
        for (ProductQuantityDTO product : currentProducts) {
            if (product.getProductId().equals(newProduct.getProductId())) {
                product.setQuantity(product.getQuantity() + newProduct.getQuantity());
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            currentProducts.add(newProduct); // 새로운 상품 추가
        }

        // 업데이트된 상품 리스트를 JSON 문자열로 저장
        this.products = ProductQuantityDTO.toJson(currentProducts);
    }
}
