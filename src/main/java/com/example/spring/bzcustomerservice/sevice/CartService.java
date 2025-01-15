package com.example.spring.bzcustomerservice.sevice;

import com.example.spring.bzcustomerservice.dto.ProductQuantityDTO;
import com.example.spring.bzcustomerservice.entity.Cart;
import com.example.spring.bzcustomerservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public void addToCart(Long customerId, Long productId, Integer quantity) {
        Optional<Cart> optionalCart = cartRepository.findByCustomerId(customerId);

        Cart cart = optionalCart.orElseGet(() -> Cart.createEmptyCart(customerId));

        String updatedProducts = Cart.addProductToCart(cart.getProducts(), new ProductQuantityDTO(productId, quantity));
        cart.setProducts(updatedProducts);

        cartRepository.save(cart);
        System.out.println("Cart updated: " + cart); // 로그 추가
    }


}

