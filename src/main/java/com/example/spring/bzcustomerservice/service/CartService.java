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

    /**
     * 장바구니에 상품 추가
     */
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
     * 장바구니 아이템 가져오기
     * @param memberNo 회원 번호
     * @return 장바구니 상품 목록
     */
    public List<ProductQuantityDTO> getCartItems(Long memberNo) {
        Cart cart = cartRepository.findByMemberNo(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 없습니다."));

        return ProductQuantityDTO.fromJson(cart.getProducts());
    }
}
