package com.example.spring.bzcustomerservice.controller;

import com.example.spring.bzcustomerservice.dto.CartRequestDTO;
import com.example.spring.bzcustomerservice.sevice.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartRequestDTO cartRequest) {
        cartService.addToCart(cartRequest.getMemberNo(), cartRequest.getProductId(), cartRequest.getQuantity());
        return ResponseEntity.ok("장바구니에 추가되었습니다.");
    }
}
