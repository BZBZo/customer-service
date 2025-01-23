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

    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartRequestDTO cartRequest) {
        cartService.addToCart(cartRequest);
        return ResponseEntity.ok("장바구니에 추가되었습니다.");
    }

    /**
     * 특정 회원(memberNo)의 장바구니 상품 목록 가져오기
     */
    @GetMapping("/cart/list")
    public ResponseEntity<List<ProductQuantityDTO>> getCartItems(@RequestParam Long memberNo) {
        List<ProductQuantityDTO> cartItems = cartService.getCartItems(memberNo);
        return ResponseEntity.ok(cartItems); // JSON으로 반환
    }
}

