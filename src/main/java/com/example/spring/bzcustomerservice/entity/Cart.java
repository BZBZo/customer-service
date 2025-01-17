package com.example.spring.bzcustomerservice.entity;

import com.example.spring.bzcustomerservice.dto.ProductQuantityDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

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

    @Column(name = "customer_id", nullable = false) // 실제 DB 컬럼 이름이 "customer_id"라면 유지
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
        Cart cart = new Cart();
        cart.setMemberNo(memberNo); // memberNo 설정
        cart.setProducts(""); // 초기 빈 장바구니 설정
        return cart;
    }

    /**
     * 상품을 장바구니에 추가하는 메서드
     * @param productList 기존 상품 목록 (JSON 형태)
     * @param newProduct 추가할 상품 정보
     * @return 업데이트된 상품 목록 (JSON 형태)
     */
    public static String addProductToCart(String productList, ProductQuantityDTO newProduct) {
        List<ProductQuantityDTO> currentProducts = ProductQuantityDTO.fromJson(productList);

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

        return ProductQuantityDTO.toJson(currentProducts);
    }
}
