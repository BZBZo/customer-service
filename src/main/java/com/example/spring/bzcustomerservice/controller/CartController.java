package com.example.spring.bzcustomerservice.controller;

import com.example.spring.bzcustomerservice.dto.CartRequestDTO;
import com.example.spring.bzcustomerservice.dto.ProductQuantityDTO;
import com.example.spring.bzcustomerservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니에 상품 추가
     */
    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartRequestDTO cartRequest) {
        cartService.addToCart(cartRequest); // CartRequestDTO 사용
        return ResponseEntity.ok("장바구니에 추가되었습니다.");
    }

    /**
     * 장바구니 상품 조회
     */
    // 장바구니 조회
    @GetMapping("/cart/list")
    public ResponseEntity<List<ProductQuantityDTO>> getCartItems(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(cartService.getCartItems(token));
    }
}
