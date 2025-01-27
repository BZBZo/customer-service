package com.example.spring.bzcustomerservice.service;

import com.example.spring.bzcustomerservice.dto.ProductQuantityDTO;
import com.example.spring.bzcustomerservice.dto.PurchaseHistoryDTO;
import com.example.spring.bzcustomerservice.entity.Cart;
import com.example.spring.bzcustomerservice.entity.Purchase;
import com.example.spring.bzcustomerservice.repository.CartRepository;
import com.example.spring.bzcustomerservice.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final CartRepository cartRepository;

    public void savePurchaseHistory(PurchaseHistoryDTO dto) {
        // 1. 구매 내역 저장
        Purchase purchase = dto.toPurchase();
        purchaseRepository.save(purchase);

        // 2. 장바구니에서 구매한 상품 삭제
        // 회원 번호를 사용하여 장바구니 조회
        Cart cart = cartRepository.findByMemberNo(dto.getMemberNo())
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 비어있습니다."));

        // 장바구니의 상품 목록 가져오기
        List<ProductQuantityDTO> currentProducts = ProductQuantityDTO.fromJson(cart.getProducts());

        // 구매한 상품 목록 가져오기
        List<ProductQuantityDTO> purchasedProducts = ProductQuantityDTO.fromJson(dto.getProductList());

        // 구매하지 않은 상품 필터링
        List<ProductQuantityDTO> updatedProducts = currentProducts.stream()
                .filter(cartProduct -> purchasedProducts.stream()
                        .noneMatch(purchasedProduct -> purchasedProduct.getProductId().equals(cartProduct.getProductId())))
                .toList();

        // 필터링된 상품 목록을 장바구니에 다시 저장
        cart.setProducts(ProductQuantityDTO.toJson(updatedProducts));
        cartRepository.save(cart);
    }

    public List<PurchaseHistoryDTO> getPurchaseListByMemberNo(Long memberNo) {
        List<Purchase> purchases = purchaseRepository.findAllByMemberNo(memberNo);
        return purchases.stream()
                .map(purchase -> PurchaseHistoryDTO.builder()
                        .orderId(purchase.getOrderId())
                        .paymentKey(purchase.getPaymentKey())
                        .totalAmount(purchase.getTotalAmount())
                        .approvedAt(purchase.getApprovedAt())
                        .method(purchase.getMethod())
                        .memberNo(purchase.getMemberNo())
                        .productList(purchase.getProductList())
                        .build())
                .collect(Collectors.toList());
    }
}
