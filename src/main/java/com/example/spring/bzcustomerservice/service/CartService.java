package com.example.spring.bzcustomerservice.service;

import com.example.spring.bzcustomerservice.dto.CartRequestDTO;
import com.example.spring.bzcustomerservice.dto.ProductQuantityDTO;
import com.example.spring.bzcustomerservice.entity.Cart;
import com.example.spring.bzcustomerservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    public void addToCart(CartRequestDTO requestDTO) {
        Cart cart = cartRepository.findByMemberNo(requestDTO.getMemberNo())
                .orElseGet(() -> Cart.createEmptyCart(requestDTO.getMemberNo()));

        // 새 상품 추가
        ProductQuantityDTO newProduct = ProductQuantityDTO.builder()
                .productId(requestDTO.getProductId())
                .quantity(requestDTO.getQuantity())
                .build();

        cart.addProductToCart(newProduct);

        // 장바구니 저장
        cartRepository.save(cart);
        log.info("Updated Cart: {}", cart.getProducts());
    }

    /**
     * 특정 회원(memberNo)의 장바구니 상품 목록 가져오기
     * @param memberNo 회원 번호
     * @return 장바구니 상품 목록
     */
    public List<ProductQuantityDTO> getCartItems(Long memberNo) {
        Cart cart = cartRepository.findByMemberNo(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 없습니다."));

        // products 컬럼(JSON)을 ProductQuantityDTO 리스트로 변환
        List<ProductQuantityDTO> productList = ProductQuantityDTO.fromJson(cart.getProducts());
        log.info("Fetched Cart Items for MemberNo {}: {}", memberNo, productList);
        return productList;
    }
}
