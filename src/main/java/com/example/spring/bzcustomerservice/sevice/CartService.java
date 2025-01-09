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

        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = new Cart();
            cart.setCustomerId(customerId);
        }

        // 기존 productId가 있으면 수량만 추가, 없으면 새로 추가
        boolean productExists = false;
        for (ProductQuantityDTO pq : cart.getProducts()) {
            if (pq.getProductId().equals(productId)) {
                pq.setQuantity(pq.getQuantity() + quantity);
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            cart.getProducts().add(new ProductQuantityDTO(productId, quantity));
        }

        cartRepository.save(cart);
    }
}

